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
        PaySuccess paySuccess = (PaySuccess) myHttpServletRequest.getRequestObject(PaySuccess.class);
        try {
            System.out.println(paySuccess);
            System.out.println(paySuccess.getAppid());
            System.out.println(paySuccess.getAttach());
            System.out.println(paySuccess.getBankType());
            
            System.out.println(paySuccess.getCashFee());
            System.out.println(paySuccess.getIsSubscribe());
            System.out.println(paySuccess.getMchId());
            System.out.println(paySuccess.getNonceStr());
            System.out.println(paySuccess.getOpenid());
            System.out.println(paySuccess.getOutTradeNo());
            System.out.println(paySuccess.getResultCode());
            System.out.println(paySuccess.getReturnCode());
            System.out.println(paySuccess.getSign());
            System.out.println(paySuccess.getTimeEnd());
            System.out.println(paySuccess.getTotalFee());
            System.out.println(paySuccess.getTradeType());
            System.out.println(paySuccess.getTransactionId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
