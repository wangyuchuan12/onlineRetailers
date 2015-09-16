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
import com.wyc.domain.TemporaryData;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.OrderDetailService;
import com.wyc.service.TemporaryDataService;
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
    @Autowired
    private TemporaryDataService temporaryDataService;
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
                Iterable<TemporaryData> temporaryDatas = temporaryDataService.findAllByMykey(outTradeNo);
                for(TemporaryData temporaryData:temporaryDatas){
                    if(temporaryData.getName().equals("goodId")){
                        httpServletRequest.setAttribute("good_id", temporaryData.getValue());
                    }
                    if(temporaryData.getName().equals("payType")){
                        httpServletRequest.setAttribute("pay_type", temporaryData.getValue());
                    }
                    if(temporaryData.getName().equals("openId")){
                        httpServletRequest.setAttribute("openId", temporaryData.getValue());
                    }
                    
                    if(temporaryData.getName().equals("userId")){
                        httpServletRequest.setAttribute("userId", temporaryData.getValue());
                    }
                }
                httpServletRequest.setAttribute("status", 2);
            }
            
        }
    }
}
