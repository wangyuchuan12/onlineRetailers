package com.wyc.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.annotation.ReadXmlRequestToObjectAnnotation;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.wx.response.domain.PaySuccess;

@RestController
public class WxApi {
    @ReadXmlRequestToObjectAnnotation(bean=PaySuccess.class)
    @RequestMapping(value = "/api/wx/pay_success")
    public void paySuccess(HttpServletRequest httpServletRequest){
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        System.out.println(myHttpServletRequest.getRequestObject(PaySuccess.class));
    }
}
