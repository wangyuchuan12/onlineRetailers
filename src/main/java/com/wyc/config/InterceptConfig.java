package com.wyc.config;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.danga.MemCached.MemCachedClient;
import com.wyc.intercept.ActionIntercept;
import com.wyc.intercept.domain.BeforeBean;
import com.wyc.intercept.domain.ResponseBean;
import com.wyc.wx.domain.WxContext;
import com.wyc.wx.service.OauthService;
import com.wyc.wx.service.UserService;

@Aspect
@Component
public class InterceptConfig {
    private ActionIntercept actionIntercept;
    @Autowired
    private UserService userService;
    @Autowired
    private OauthService oauthService;
    @Autowired
    private WxContext wxContext;
    @Autowired
    private MemCachedClient memCachedClient;
    final static Logger logger = LoggerFactory.getLogger(InterceptConfig.class);
    
    @Around(value="execution (* com.wyc.wx.service.*.*(..))")
    public Object aroundWxService(ProceedingJoinPoint proceedingJoinPoint){
            logger.debug("execution (* com.wyc.wx.service.*.*(..))");
            System.out.println("jin laile <<<<<<<<<<<<<<<<<");
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
            try {
                if(wxContext.getFlag().equals("1")){
                    Object url = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
                    return url;
                }
                actionIntercept = new ActionIntercept();
                actionIntercept.setOauthService(oauthService);
                actionIntercept.setUserService(userService);
                if(proceedingJoinPoint.getArgs()!=null&&proceedingJoinPoint.getArgs().length>0){
                    actionIntercept.setHttpServletRequest((HttpServletRequest) proceedingJoinPoint.getArgs()[0]);
                }
                BeforeBean beforeBean = actionIntercept.before();
                if(beforeBean.isEnalble()){
                    ResponseBean responseBean = actionIntercept.response(beforeBean);
                    return proceedingJoinPoint.proceed();
                }else{
                    return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?response_type=code&scope=snsapi_base&state=123&appid="+wxContext.getAppid()+"&connect_redirect=1#wechat_redirect";
                }
                
                
               
            } catch (Throwable e) {
                for(StackTraceElement stackTraceElement:e.getStackTrace()){
                    logger.error("has an error {}",stackTraceElement);
                }
                e.printStackTrace();
               return "info/TradeFlowInfo";
            }
    }
}
