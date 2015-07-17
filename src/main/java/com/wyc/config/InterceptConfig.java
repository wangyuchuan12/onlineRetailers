package com.wyc.config;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wyc.intercept.ResponseUserInfoByCodeIntercept;
import com.wyc.intercept.domain.BeforeBean;
import com.wyc.intercept.domain.ResponseBean;
import com.wyc.wx.service.OauthService;
import com.wyc.wx.service.UserService;

@Aspect
@Component
public class InterceptConfig {
    private ResponseUserInfoByCodeIntercept intercept;
    @Autowired
    private UserService userService;
    @Autowired
    private OauthService oauthService;
    @Around(value="execution (* com.wyc.controller.action.*.*(..))")
    public Object aroundAction(ProceedingJoinPoint proceedingJoinPoint){

            try {
                intercept = new ResponseUserInfoByCodeIntercept();
                intercept.setOauthService(oauthService);
                intercept.setUserService(userService);
                if(proceedingJoinPoint.getArgs()!=null&&proceedingJoinPoint.getArgs().length>0){
                    intercept.setHttpServletRequest((HttpServletRequest) proceedingJoinPoint.getArgs()[0]);
                }
                BeforeBean beforeBean = intercept.before();
                if(beforeBean.isEnalble()){
                    ResponseBean responseBean = intercept.response(beforeBean);
                    return proceedingJoinPoint.proceed();
                }else{
                    return null;
                }
                
                
               
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
    }
}
