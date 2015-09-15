package com.wyc.controller.api;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.annotation.AfterHandlerAnnotation;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.annotation.handler.PayResultHandler;

@RestController
public class GoodsApi {
    
    @RequestMapping(value = "/api/pay_success")
    @UserInfoFromWebAnnotation
    @AfterHandlerAnnotation(hanlerClasses={PayResultHandler.class})
    public Object paySouccess(HttpServletRequest httpServletRequest)throws Exception{
        
        String goodId = httpServletRequest.getParameter("good_id");
        String payType = httpServletRequest.getParameter("pay_type");
     //   String status = httpServletRequest.getParameter("status");
        
        httpServletRequest.setAttribute("good_id", goodId);
        httpServletRequest.setAttribute("payt_type", payType);
        httpServletRequest.setAttribute("status", 2);
        return null;

    }
}
