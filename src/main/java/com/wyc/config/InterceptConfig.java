package com.wyc.config;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.danga.MemCached.MemCachedClient;
import com.wyc.annotation.AccessTokenAnnotation;
import com.wyc.annotation.AuthorizeAnnotation;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.httpdecorate.AccessTokenDecorate;
import com.wyc.httpdecorate.AuthorizeDecorate;
import com.wyc.httpdecorate.UserInfoFromWebDecorate;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.domain.WxContext;
import com.wyc.wx.service.OauthService;
import com.wyc.wx.service.UserService;

@Aspect
@Component
public class InterceptConfig {
    @Autowired
    private UserService userService;
    @Autowired
    private OauthService oauthService;
    @Autowired
    private WxContext wxContext;
    @Autowired
    private MemCachedClient memCachedClient;
    @Autowired
    private AutowireCapableBeanFactory factory;
    
    final static Logger logger = LoggerFactory.getLogger(InterceptConfig.class);
    
    @Around(value="execution (* com.wyc.wx.service.*.*(..))")
    public Object aroundWxService(ProceedingJoinPoint proceedingJoinPoint){
            logger.debug("execution (* com.wyc.wx.service.*.*(..))");
            Object[] args = proceedingJoinPoint.getArgs();
            StringBuffer sb = new StringBuffer();
            sb.append(proceedingJoinPoint.getTarget().getClass());
            sb.append("-");
            for(Object obj:args){
                if(obj!=null){
                    sb.append(obj);
                }
            }
            Object object = memCachedClient.get(sb.toString());
            if(object==null){
                try {
                    object = proceedingJoinPoint.proceed(args);
                    memCachedClient.set(sb.toString(), object);
                    logger.debug("get "+object+" from "+"database");
                } catch (Throwable e) {
                    StringBuffer sb2 = new StringBuffer();
                    for(StackTraceElement stackTraceElement:e.getStackTrace()){
                        sb2.append(stackTraceElement);
                    }
                    logger.error(sb2.toString());
                }
              
            }else{
                logger.debug("get "+object+" from "+"memCachedClient");
            }
       
        return object;
    }
    @Around(value="execution (* com.wyc.controller.action.*.*(..))")
    public Object aroundAction(ProceedingJoinPoint proceedingJoinPoint){
        Object target = proceedingJoinPoint.getTarget();
        Object[] args  = proceedingJoinPoint.getArgs();
        HttpServletRequest httpServletRequest = (HttpServletRequest)args[0];
        Method method = null;
        
        for(Method oneMethod:target.getClass().getMethods()){
            if(oneMethod.getName().equals(proceedingJoinPoint.getSignature().getName())){
                method = oneMethod;
                break;
            }
        }
        
        
        MyHttpServletRequest myHttpServletRequest = null;
        
      //注入accessToken的逻辑
        try {
            if(method.getAnnotation(AccessTokenAnnotation.class)!=null){
                myHttpServletRequest = autoAccessToken(httpServletRequest); 
              
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        
        
        //注入authorize的逻辑
        try {
            if(method.getAnnotation(AuthorizeAnnotation.class)!=null){
                String code = httpServletRequest.getParameter("code");
                if(myHttpServletRequest!=null){
                       myHttpServletRequest = autoAuthorize(myHttpServletRequest,code);
                }else{
                       myHttpServletRequest = autoAuthorize(httpServletRequest,code);
                    
                } 
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        
        
        //注入userinfo的逻辑，依赖于authorize
        try {
            if(method.getAnnotation(UserInfoFromWebAnnotation.class)!=null){
                if(myHttpServletRequest!=null){
                   if(myHttpServletRequest.getAuthorize()==null){
                       String code = httpServletRequest.getParameter("code");
                       myHttpServletRequest = autoAuthorize(myHttpServletRequest, code);
                       myHttpServletRequest = autoUserInfoFromWeb(httpServletRequest, myHttpServletRequest.getAuthorize());
                   }else{
                       myHttpServletRequest = autoUserInfoFromWeb(httpServletRequest, myHttpServletRequest.getAuthorize());
                   }
                }else{
                    String code = httpServletRequest.getParameter("code");
                    myHttpServletRequest = autoAuthorize(httpServletRequest, code);
                    myHttpServletRequest = autoUserInfoFromWeb(httpServletRequest, myHttpServletRequest.getAuthorize());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(myHttpServletRequest!=null){
            args[0] = myHttpServletRequest;
        }
        
        
        try {
            Object url = proceedingJoinPoint.proceed(args);
            return url;
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    private MyHttpServletRequest autoAccessToken(HttpServletRequest httpServletRequest)throws Exception{
        AccessTokenDecorate accessTokenDecorate = new AccessTokenDecorate(httpServletRequest);
        factory.autowireBean(accessTokenDecorate);
        MyHttpServletRequest myHttpServletRequest = accessTokenDecorate.execute();
        return myHttpServletRequest;
        
    }
    
//    private MyHttpServletRequest autoAccessToken(MyHttpServletRequest httpServletRequest)throws Exception{
//        AccessTokenDecorate accessTokenDecorate = new AccessTokenDecorate(httpServletRequest);
//        factory.autowireBean(accessTokenDecorate);
//        MyHttpServletRequest myHttpServletRequest = accessTokenDecorate.execute();
//        return myHttpServletRequest;
//        
//    }
    
    private MyHttpServletRequest autoAuthorize(HttpServletRequest httpServletRequest,String code)throws Exception{
        AuthorizeDecorate authorizeDecorate = new AuthorizeDecorate(httpServletRequest, code);
        factory.autowireBean(authorizeDecorate);
        MyHttpServletRequest myHttpServletRequest = authorizeDecorate.execute();
        return myHttpServletRequest;
    }
    
    private MyHttpServletRequest autoAuthorize(MyHttpServletRequest httpServletRequest,String code)throws Exception{
        AuthorizeDecorate authorizeDecorate = new AuthorizeDecorate(httpServletRequest, code);
        factory.autowireBean(authorizeDecorate);
        MyHttpServletRequest myHttpServletRequest = authorizeDecorate.execute();
        return myHttpServletRequest;
    }
    
    private MyHttpServletRequest autoUserInfoFromWeb(HttpServletRequest httpServletRequest,Authorize authorize)throws Exception{
        UserInfoFromWebDecorate userInfoFromWebDecorate = new UserInfoFromWebDecorate(httpServletRequest, authorize);
        factory.autowireBean(userInfoFromWebDecorate);
        MyHttpServletRequest myHttpServletRequest = userInfoFromWebDecorate.execute();
        return myHttpServletRequest;
    }
    
//    private MyHttpServletRequest autoUserInfoFromWeb(MyHttpServletRequest httpServletRequest,Authorize authorize)throws Exception{
//        UserInfoFromWebDecorate userInfoFromWebDecorate = new UserInfoFromWebDecorate(httpServletRequest, authorize);
//        factory.autowireBean(userInfoFromWebDecorate);
//        MyHttpServletRequest myHttpServletRequest = userInfoFromWebDecorate.execute();
//        return myHttpServletRequest;
//    }
}
