package com.wyc.controller.api;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.annotation.AfterHandlerAnnotation;
import com.wyc.annotation.ResponseJson;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.annotation.handler.PayResultHandler;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.wx.domain.UserInfo;

@RestController
public class GoodsApi {
    
    @RequestMapping(value = "/api/pay_failure")
    @UserInfoFromWebAnnotation
    @AfterHandlerAnnotation(hanlerClasses={PayResultHandler.class})
    @ResponseJson(names={"orderId"})
    public Object payFailure(HttpServletRequest httpServletRequest)throws Exception{
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        String goodId = httpServletRequest.getParameter("good_id");
        String payType = httpServletRequest.getParameter("pay_type");
     //   String status = httpServletRequest.getParameter("status");
        
        httpServletRequest.setAttribute("good_id", goodId);
        httpServletRequest.setAttribute("pay_type", payType);
        httpServletRequest.setAttribute("status", 1);
        httpServletRequest.setAttribute("openId", userInfo.getOpenid());
        httpServletRequest.setAttribute("userId", userInfo.getId());
        return null;

    }
}
