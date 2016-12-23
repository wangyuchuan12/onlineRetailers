package com.wyc.annotation.handler;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleIfStatement.ElseIf;
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
        String isInsteadOfReceiving = httpServletRequest.getParameter("isInsteadOfReceiving");
        if(isInsteadOfReceiving==null){
        	isInsteadOfReceiving = httpServletRequest.getSession().getAttribute("isInsteadOfReceiving").toString();
        }
        String isFindOtherInsteadOfReceiving = httpServletRequest.getParameter("isFindOtherInsteadOfReceiving");
        if(isFindOtherInsteadOfReceiving==null){
        	isFindOtherInsteadOfReceiving = httpServletRequest.getSession().getAttribute("isFindOtherInsteadOfReceiving").toString();
        }
        String isReceiveGoodsTogether = httpServletRequest.getParameter("isReceiveGoodsTogether");
        if(isReceiveGoodsTogether==null){
        	isReceiveGoodsTogether = httpServletRequest.getSession().getAttribute("isReceiveGoodsTogether").toString();
        }
        Good good = goodService.findOne(goodId);
        BigDecimal reliefValue = null;
        if(payType.equals("0")){
        	if(isReceiveGoodsTogether.equals("1")){
        		reliefValue = good.getForceInsteadOfRelief();
        	}else if(isInsteadOfReceiving.equals("1")){
        		reliefValue = good.getAllowInsteadOfRelief();
        	}else{
        		reliefValue = new BigDecimal(0);
        	}
        }else if(payType.equals("3")){
        	if(isFindOtherInsteadOfReceiving.equals("1")){
        		reliefValue = good.getInsteadOfRelief();
        	}else if(isInsteadOfReceiving.equals("1")){
        		reliefValue = good.getAllowInsteadOfRelief();
        	}else{
        		reliefValue = new BigDecimal(0);
        	}
        }else{
        	reliefValue = new BigDecimal(0);
        }
        logger.debug("检测payType为："+payType);
        BigDecimal cost = new BigDecimal(0);
        //0表示团购 1表示单独买 2表示开团劵购买 3参团购买
        if(payType.equals("0")||payType.equals("3")){
        	cost = good.getGroupDiscount().multiply(good.getGroupOriginalCost());
        
        	cost = cost.add(good.getFlowPrice());
        	cost = cost.subtract(reliefValue);
        	
        	logger.debug("检测cost为："+cost);
        	logger.debug("检测reliefValue为："+reliefValue);
        	
            
        }else if (payType.equals("1")) {
        	
        	cost = good.getAloneDiscount().multiply(good.getAloneOriginalCost());
            
        	cost = cost.add(good.getFlowPrice());
        	cost = cost.subtract(reliefValue);
        }else if (payType.equals("2")) {
            cost = good.getFlowPrice();
        }
        if(cost.intValue()<0){
        	cost = new BigDecimal(0.01);
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
