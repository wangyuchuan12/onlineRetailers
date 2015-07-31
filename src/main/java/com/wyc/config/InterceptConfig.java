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
import com.wyc.httpdecorate.AccessTokenDecorate;
import com.wyc.httpdecorate.AuthorizeDecorate;
import com.wyc.httpdecorate.DecorateFactory;
import com.wyc.httpdecorate.UserInfoFromWebDecorate;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.TokenService;
import com.wyc.service.WxAccessTokenService;
import com.wyc.service.WxAuthorizeService;
import com.wyc.service.WxUserInfoService;
import com.wyc.wx.domain.AccessTokenBean;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.domain.Token;
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
    private DecorateFactory decorateFactory;
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private WxAccessTokenService wxAccessTokenService;
    @Autowired
    private WxAuthorizeService wxAuthorizeService;
    @Autowired
    private WxUserInfoService wxUserInfoService;
    final static Logger logger = LoggerFactory.getLogger(InterceptConfig.class);
    
 //   @Around(value="execution (* com.wyc.wx.service.*.*(..))")
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
      //注入accessToken的逻辑
        try {
            if(method.getAnnotation(AccessTokenAnnotation.class)!=null){
               Token token = tokenService.findByIdAndInvalidDateGreaterThan(tokenId, new DateTime());
               if(token==null){
                   AccessTokenDecorate accessTokenDecorate = decorateFactory.accessTokenDecorate(myHttpServletRequest);
                   accessTokenDecorate.execute();
                   logger.debug("inject accessToken to myHttpServletRequest the acessToken is {}",myHttpServletRequest.getAccessTokenBean());
                   token = new Token();
                   token.setStatus(1);
                   tokenService.add(token);
                   
               }else{
                   AccessTokenBean accessTokenBean = wxAccessTokenService.findByToken(tokenId);
                   if(accessTokenBean!=null){
                       myHttpServletRequest.setAccessTokenBean(accessTokenBean);
                   }else{
                       AccessTokenDecorate accessTokenDecorate = decorateFactory.accessTokenDecorate(myHttpServletRequest);
                       accessTokenDecorate.execute();
                       accessTokenBean = myHttpServletRequest.getAccessTokenBean();
                       wxAccessTokenService.add(accessTokenBean);
                   }
                   accessTokenBean.setToken(token.getId());
               }
               myHttpServletRequest.setToken(token);
               
            }
        } catch (Exception e) {
            logger.debug("inject accessToken to myHttpServletRequest has error");
            e.printStackTrace();
        }
        
        
        //注入authorize的逻辑
        try {
            if(method.getAnnotation(AuthorizeAnnotation.class)!=null){
                String code = httpServletRequest.getParameter("code");
               
                AuthorizeDecorate authorizeDecorate = decorateFactory.authorizeDecorate(myHttpServletRequest, code);
                authorizeDecorate.execute();
                logger.debug("the code is {}",code);
                logger.debug("inject Authorize to myHttpServletRequest success , the Authorize is {}",myHttpServletRequest.getAuthorize());
            }
        } catch (Exception e) {
            logger.debug("inject Authorize to myHttpServletRequest has error");
           e.printStackTrace();
        }
        
        
        //注入userinfo的逻辑，依赖于authorize
        try {
            if(method.getAnnotation(UserInfoFromWebAnnotation.class)!=null){
                UserInfoFromWebDecorate userInfoFromWebDecorate = decorateFactory.infoFromWebDecorate(myHttpServletRequest, myHttpServletRequest.getAuthorize());               
                userInfoFromWebDecorate.execute();
                logger.debug("inject UserInfo to myHttpServletRequest success , the UserInfo is {}",myHttpServletRequest.getUserInfo());
            }
        } catch (Exception e) {
            logger.debug("inject UserInfo from web to myHttpServletRequest has error");
            e.printStackTrace();
        }
        
        
        
        
        if(myHttpServletRequest!=null){
            args[0] = myHttpServletRequest;
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
