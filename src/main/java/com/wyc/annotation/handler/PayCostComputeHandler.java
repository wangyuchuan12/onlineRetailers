package com.wyc.annotation.handler;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;

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
        BigDecimal reliefValue = null;
        if(payType.equals("0")){
        	String reliefType = httpServletRequest.getParameter("reliefType");
        	if(reliefType.equals("1")){
        		reliefValue = good.getAllowInsteadOfRelief();
        	}else if(reliefType.equals("2")){
        		reliefValue = good.getForceInsteadOfRelief();
        	}else{
        		reliefValue = new BigDecimal(0);
        	}
        }else{
        	reliefValue = new BigDecimal(0);
        }
        Float cost = null;
        //0表示团购 1表示单独买 2表示开团劵购买
        if(payType.equals("0")||payType.equals("3")){
            cost = good.getFlowPrice()+good.getGroupDiscount()*good.getGroupOriginalCost()-reliefValue.floatValue();
        }else if (payType.equals("1")) {
            cost = good.getFlowPrice()+good.getAloneDiscount()*good.getAloneOriginalCost()-reliefValue.floatValue();
        }else if (payType.equals("2")) {
            cost = good.getFlowPrice()-reliefValue.floatValue();
        }
        if(cost<0){
        	cost = 0f;
        }
        
        logger.debug("the cost is:{}",cost);
        httpServletRequest.setAttribute("cost", cost);
        httpServletRequest.setAttribute("reliefValue", reliefValue);
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
