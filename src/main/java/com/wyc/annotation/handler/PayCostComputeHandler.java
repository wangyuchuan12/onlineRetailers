package com.wyc.annotation.handler;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.domain.Good;
import com.wyc.service.GoodService;


public class PayCostComputeHandler implements Handler{
    final static Logger logger = LoggerFactory.getLogger(PayCostComputeHandler.class);
    @Autowired
    private GoodService goodService;
    @Override
    public Object handle(HttpServletRequest httpServletRequest)throws Exception{
        String goodId = httpServletRequest.getParameter("good_id");
        String payType=httpServletRequest.getParameter("pay_type");
        Good good = goodService.findOne(goodId);
        Float cost = null;
        //0表示团购 1表示单独买 2表示开团劵购买
        if(payType.equals("0")){
            cost = good.getFlowPrice()+good.getGroupDiscount()*good.getGroupOriginalCost();
        }else if (payType.equals("1")) {
            cost = good.getFlowPrice()+good.getAloneDiscount()*good.getAloneOriginalCost();
        }else if (payType.equals("2")) {
            cost = good.getFlowPrice();
        }
        httpServletRequest.setAttribute("cost", cost);
        return cost;
    }
    @Override
    public Class<Handler>[] extendHandlers() {
        return null;
    }
    @Override
    public void setAnnotation(Annotation annotation) {
        // TODO Auto-generated method stub
        
    }
   
   
}
