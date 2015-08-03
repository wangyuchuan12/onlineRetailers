package com.wyc.config;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.danga.MemCached.MemCachedClient;
import com.wyc.annotation.AccessTokenAnnotation;
import com.wyc.annotation.AuthorizeAnnotation;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.TokenService;
import com.wyc.smart.service.AccessTokenSmartService;
import com.wyc.smart.service.AuthorizeSmartService;
import com.wyc.smart.service.UserSmartService;
import com.wyc.wx.domain.AccessTokenBean;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.domain.Token;
import com.wyc.wx.domain.UserInfo;
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

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AccessTokenSmartService accessTokenSmartService;
    @Autowired
    private AuthorizeSmartService authorizeSmartService;
    @Autowired
    private UserSmartService userSmartService;
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
                        sb2.append(stackTraceElement+"\r\n");
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
        logger.debug("around target is {}",target);
        Object[] args  = proceedingJoinPoint.getArgs();
        String str = null;
        for(Object arg:args){
            str+=arg+",";
        }
        logger.debug("the args is {}",str);
        HttpServletRequest httpServletRequest = (HttpServletRequest)args[0];
        Method method = null;
      
        for(Method oneMethod:target.getClass().getMethods()){
            if(oneMethod.getName().equals(proceedingJoinPoint.getSignature().getName())){
                method = oneMethod;
                break;
            }
        }
        
        logger.debug("invoke the method is {}",method);
        MyHttpServletRequest myHttpServletRequest = new MyHttpServletRequest(httpServletRequest);
        String tokenId = myHttpServletRequest.getParameter("token");
        Token token = null;
        if(tokenId!=null){
            token = tokenService.findByIdAndInvalidDateGreaterThan(tokenId, new DateTime());
        }
        
        if(method.getAnnotation(AccessTokenAnnotation.class)!=null){
            if(token!=null){
                myHttpServletRequest.setAccessTokenBean(accessTokenSmartService.getFromDatabase(token.getId()));
            }else{
                try {
                    AccessTokenBean accessTokenBean = accessTokenSmartService.getFromWx();
                    if(accessTokenBean!=null){
                        myHttpServletRequest.setAccessTokenBean(accessTokenBean);
                        token = accessTokenSmartService.saveToDatabase(accessTokenBean);
                    }
                } catch (Exception e) {
                   logger.error("get accessToken from wx error");
                   e.printStackTrace();
                }
                
            }
        }
        if(method.getAnnotation(AuthorizeAnnotation.class)!=null){
            if(token!=null){
                Authorize authorize = authorizeSmartService.getFromDatabase(token.getId());
                myHttpServletRequest.setAuthorize(authorize);
            }else{
                try {
                    Authorize authorize = authorizeSmartService.getFromWx();
                    if(authorize!=null){
                        myHttpServletRequest.setAuthorize(authorize);
                        token = authorizeSmartService.saveToDatabase(authorize);
                    }
                } catch (Exception e) {
                    logger.error("get authorize from wx error");
                    e.printStackTrace();
                }
                
            }
        }
        
        if(method.getAnnotation(UserInfoFromWebAnnotation.class)!=null){
            logger.debug("the method has UserInfoFromWebAnnotation");
            String openid = myHttpServletRequest.getParameter("openid");
            if(openid!=null){
                userSmartService.setOpenid(openid);
            }
            UserInfo userInfo = userSmartService.getFromDatabase(token.getId());
            if(userInfo==null){
                try {
                    userSmartService.setCode(myHttpServletRequest.getParameter("code"));
                    userInfo = userSmartService.getFromWx();
                    if(userInfo!=null){
                        
                        token = userSmartService.saveToDatabase(userInfo);
                    }
                } catch (Exception e) {
                    logger.error("get userInfo from wx error");
                    e.printStackTrace();
                    
                }
            }
            myHttpServletRequest.setUserInfo(userInfo);
        }
        
        
        
        if(myHttpServletRequest!=null){
            args[0] = myHttpServletRequest;
        }
        if(token!=null){
            myHttpServletRequest.setAttribute("token", token.getId());
        }
        try {
            Object url = proceedingJoinPoint.proceed(args);
            logger.debug("return url is {}",url);
            return url;
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            logger.error("invoke action method has error");
            e.printStackTrace();
        }
        return null;
    }
}
