package com.wyc.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.annotation.BeforeHandlerAnnotation;
import com.wyc.annotation.ResultBean;
import com.wyc.annotation.handler.ReadXmlRequestToObjectHandler;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.wx.response.domain.PaySuccess;

@RestController
public class WxApi {
    @BeforeHandlerAnnotation(hanlerClasses=ReadXmlRequestToObjectHandler.class)
    @ResultBean(bean=PaySuccess.class)
    @RequestMapping(value = "/api/wx/pay_success")
    public void paySuccess(HttpServletRequest httpServletRequest){
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        PaySuccess paySuccess = (PaySuccess) myHttpServletRequest.getRequestObject(PaySuccess.class);
        String outTradeNo = paySuccess.getOutTradeNo();
        
    }
}
