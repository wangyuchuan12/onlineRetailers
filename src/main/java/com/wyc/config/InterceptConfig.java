package com.wyc.config;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    @Around(value="execution (* com.wyc.controller.action.*.*(..))")
    public Object aroundAction(ProceedingJoinPoint proceedingJoinPoint){
            System.out.println(wxContext.getFlag());
            try {
                if(wxContext.getFlag().equals("1")){
                    Object url = proceedingJoinPoint.proceed();
                    System.out.println(url);
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
    }
}
