package com.wyc.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.annotation.AfterHandlerAnnotation;
import com.wyc.annotation.BeforeHandlerAnnotation;
import com.wyc.annotation.ResultBean;
import com.wyc.annotation.handler.PayResultHandler;
import com.wyc.annotation.handler.ReadXmlRequestToObjectHandler;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.OrderDetailService;
import com.wyc.service.WxPaySuccessService;
import com.wyc.wx.response.domain.PaySuccess;

@RestController
public class WxApi {
    @Autowired
    private WxPaySuccessService wxPaySuccessService;
    @Autowired
    private GoodsApi goodsApi;
    @Autowired
    private OrderDetailService orderDetailService;
    @BeforeHandlerAnnotation(hanlerClasses=ReadXmlRequestToObjectHandler.class)
    @ResultBean(bean=PaySuccess.class)
    @AfterHandlerAnnotation(hanlerClasses=PayResultHandler.class)
    @RequestMapping(value = "/api/wx/pay_success")
    public void paySuccess(HttpServletRequest httpServletRequest)throws Exception{
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        PaySuccess paySuccess = (PaySuccess) myHttpServletRequest.getRequestObject(PaySuccess.class);
        String outTradeNo = paySuccess.getOutTradeNo();
        if(wxPaySuccessService.findByOutTradeNo(outTradeNo)!=null){
            
        }else{
            paySuccess = wxPaySuccessService.add(paySuccess);
            if(paySuccess.getResultCode().toLowerCase().equals("success")){
                String goodId = httpServletRequest.getParameter("good_id");
                String payType = httpServletRequest.getParameter("pay_type");
                httpServletRequest.setAttribute("good_id", goodId);
                httpServletRequest.setAttribute("pay_type", payType);
                httpServletRequest.setAttribute("status", 2);
            }
            
        }
    }
}
