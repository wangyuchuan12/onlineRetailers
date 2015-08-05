package com.wyc.config;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    
    @After(value="execution (* com.wyc.controller.action.*.*(..))")
    public void beforeAction(JoinPoint joinPoint){
        HttpServletRequest httpServletRequest = (HttpServletRequest)joinPoint.getArgs()[0];
        System.out.println(httpServletRequest.getClass());
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
        
        if(method.getAnnotation(AccessTokenAnnotation.class)!=null){
            
            AccessTokenBean accessTokenBean = null;
            if(tokenId!=null){
                accessTokenBean = accessTokenSmartService.getFromDatabase(tokenId);
                logger.debug("get accessTokenBean from database by token {} , return object is {}",tokenId , accessTokenBean);
            }
            String accessToken = myHttpServletRequest.getParameter("access_token");
            accessTokenSmartService.setAccessToken(accessToken);
            if(accessTokenBean==null&&accessToken!=null){
                accessTokenBean = accessTokenSmartService.getFromDatabaseByOther();
                logger.debug("get accessTokenBean from database by accessToken {} , return object is {}",accessToken , accessTokenBean);
            }
            String key = accessTokenSmartService.generateKey();
            if(accessTokenBean==null){
                
                accessTokenBean = accessTokenSmartService.getFromDatabaseByKey(key);
                logger.debug("get accessTokenBean from database by key {} , return object is {}",key , accessTokenBean);
            }
            
            if(accessTokenBean==null){
                try {
                    accessTokenBean = accessTokenSmartService.getFromWx();
                    logger.debug("get accessTokenBean from wx , return object is {}", accessTokenBean);
                } catch (Exception e) {
                    logger.error("get accessTokenBean from wx has error");
                    e.printStackTrace();
                }
                
                token = accessTokenSmartService.saveToDatabase(accessTokenBean, key);
                logger.debug("save to database success ,the key is {} , the token is " , key , token);
                
            }
            myHttpServletRequest.setAccessTokenBean(accessTokenBean);
        }
        if(method.getAnnotation(AuthorizeAnnotation.class)!=null){
            Authorize authorize = null;
            if(tokenId!=null){
                authorize = authorizeSmartService.getFromDatabase(tokenId);
                logger.debug("get authorize from database by token {} , return object is {}",tokenId , authorize);
            }
            String key = authorizeSmartService.generateKey();
            if(authorize==null){
                authorize = authorizeSmartService.getFromDatabaseByKey(key);
                logger.debug("get authorize from database by key {} , return object is {}",key , authorize);
            }
            if(authorize==null){
                try {
                    authorize = authorizeSmartService.getFromWx();
                } catch (Exception e) {
                    logger.error("get authorize from wx has error");
                    e.printStackTrace();
                }
                
                token = authorizeSmartService.saveToDatabase(authorize, key);
                logger.debug("save to database success ,the key is {} , the token is " , key , token);
            }
            myHttpServletRequest.setAuthorize(authorize);
        }
        
        if(method.getAnnotation(UserInfoFromWebAnnotation.class)!=null){
            UserInfo userInfo = null;
            if(tokenId!=null){
                userInfo = userSmartService.getFromDatabase(tokenId);
                logger.debug("get userInfo from database by token {} , return object is {}",tokenId , userInfo);
            }
            String key = userSmartService.generateKey();
            if(userInfo==null){
                userInfo = userSmartService.getFromDatabaseByKey(key);
                logger.debug("get userInfo from database by key {} , return object is {}",key , userInfo);
            }
            if(userInfo==null){
                try {
                    String code = myHttpServletRequest.getParameter("code");
                    userSmartService.setCode(code);
                    userInfo = userSmartService.getFromWx();
                } catch (Exception e) {
                    logger.error("get userInfo from wx has error");
                    e.printStackTrace();
                }
                
                token = userSmartService.saveToDatabase(userInfo, key);
                logger.debug("save to database success ,the key is {} , the token is " , key , token);
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
