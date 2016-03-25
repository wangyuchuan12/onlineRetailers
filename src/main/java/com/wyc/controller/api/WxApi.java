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
import com.wyc.defineBean.StopToAfter;
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
    public Object paySuccess(HttpServletRequest httpServletRequest)throws Exception{
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        PaySuccess paySuccess = (PaySuccess) myHttpServletRequest.getRequestObject(PaySuccess.class);
        if(!paySuccess.getResultCode().equals("SUCCESS")){
            return new StopToAfter();
        }
        
        String payType = myHttpServletRequest.getParameter("pay_type");
        if(payType!=null&&payType.equals("2")){
        	String outTradeNo = httpServletRequest.getParameter("outTradeNo");
        	httpServletRequest.setAttribute("outTradeNo", outTradeNo);
        	return null;
        }
        
        String outTradeNo = paySuccess.getOutTradeNo();
       
        if(wxPaySuccessService.findByOutTradeNo(outTradeNo)!=null){
            return new StopToAfter();
        }else{
            paySuccess = wxPaySuccessService.add(paySuccess);
            if(paySuccess.getResultCode().toLowerCase().equals("success")){
                httpServletRequest.setAttribute("outTradeNo", paySuccess.getOutTradeNo());
                return null;
            }
            return new StopToAfter();
            
        }
    }
}
