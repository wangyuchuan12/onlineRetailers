package com.wyc.controller.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.Good;
import com.wyc.domain.MyResource;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.GoodService;
import com.wyc.service.MyResourceService;
@Controller
public class GoodsAction {
        @Autowired
        private MyResourceService resourceService;
        @Autowired
        private GoodService goodService;
        final static Logger logger = LoggerFactory.getLogger(GoodsAction.class);
	@RequestMapping("/main/good_list")
//	@UserInfoFromWebAnnotation
	public String goodList(HttpServletRequest httpRequest){
//	        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpRequest;
//	        System.out.println(myHttpServletRequest.getUserInfo().getNickname());
	        Iterable<Good> databaseGoods = goodService.findAll();
		List<Map<String, Object>> responseGoods = new ArrayList<Map<String, Object>>();
		for(Good good:databaseGoods){
		    Map<String, Object> responseGood = new HashMap<String, Object>();
		    responseGood.put("id", good.getId());
		    responseGood.put("instruction", good.getInstruction());
		    responseGood.put("name", good.getName());
		    responseGood.put("alone_discount", good.getAloneDiscount());
		    responseGood.put("alone_original_cost", good.getAloneOriginalCost());
		    responseGood.put("flow_price", good.getFlowPrice());
		    responseGood.put("group_discount", good.getGroupDiscount());
		    responseGood.put("group_num", good.getGroupNum());
		    responseGood.put("group_original_cost", good.getGroupOriginalCost());
		    responseGood.put("market_price", good.getMarketPrice());
		    responseGood.put("coupon_cost", good.getCouponCost());
		    MyResource myResource = resourceService.findOne(good.getHeadImg());
		    if(myResource!=null){
		        responseGood.put("head_img", myResource.getUrl());
		    }
		    responseGoods.add(responseGood);
		}
		httpRequest.setAttribute("goods", responseGoods);
		return "main/Goods";
	}
	
	@RequestMapping("/info/good_info")
	public String goodInfo(HttpServletRequest httpRequest){
	    String goodId = httpRequest.getParameter("id");
	    Good good = goodService.findOne(goodId);
	    Map<String, Object> responseGood = new HashMap<String, Object>();
            responseGood.put("id", good.getId());
            responseGood.put("instruction", good.getInstruction());
            responseGood.put("name", good.getName());
            responseGood.put("alone_discount", good.getAloneDiscount());
            responseGood.put("alone_original_cost", good.getAloneOriginalCost());
            responseGood.put("flow_price", good.getFlowPrice());
            responseGood.put("group_discount", good.getGroupDiscount());
            responseGood.put("group_num", good.getGroupNum());
            responseGood.put("group_original_cost", good.getGroupOriginalCost());
            responseGood.put("market_price", good.getMarketPrice());
            responseGood.put("coupon_cost", good.getCouponCost());
            responseGood.put("group_cost", good.getGroupDiscount()*good.getGroupOriginalCost());
            responseGood.put("alone_cost", good.getAloneDiscount()*good.getAloneOriginalCost());
            MyResource myResource = resourceService.findOne(good.getHeadImg());
            if(myResource!=null){
                responseGood.put("head_img", myResource.getUrl());
            }
            httpRequest.setAttribute("good", responseGood);
            return "info/GoodInfo";
	}
	
	@RequestMapping("/info/good_info_pay")
	public String gootInfoPay(HttpServletRequest httpRequest){
	    String payType=httpRequest.getParameter("pay_type");
	    String goodId = httpRequest.getParameter("good_id");
	    String code = httpRequest.getParameter("code");
	    Good good = goodService.findOne(goodId);
            Map<String, Object> responseGood = new HashMap<String, Object>();
            responseGood.put("id", good.getId());
            responseGood.put("instruction", good.getInstruction());
            responseGood.put("name", good.getName());
            responseGood.put("alone_discount", good.getAloneDiscount());
            responseGood.put("alone_original_cost", good.getAloneOriginalCost());
            responseGood.put("flow_price", good.getFlowPrice());
            responseGood.put("group_discount", good.getGroupDiscount());
            responseGood.put("group_num", good.getGroupNum());
            responseGood.put("group_original_cost", good.getGroupOriginalCost());
            responseGood.put("market_price", good.getMarketPrice());
            responseGood.put("coupon_cost", good.getCouponCost());
            responseGood.put("group_cost", good.getGroupDiscount()*good.getGroupOriginalCost());
            responseGood.put("alone_cost", good.getAloneDiscount()*good.getAloneOriginalCost());
            responseGood.put("pay_type", payType);
            
            if(payType.equals("0")){
                responseGood.put("cost",good.getFlowPrice()+good.getGroupDiscount()*good.getGroupOriginalCost());
            }else if (payType.equals("1")) {
                responseGood.put("cost",good.getAloneDiscount()*good.getAloneOriginalCost());
            }else if (payType.equals("2")) {
                responseGood.put("cost",good.getFlowPrice());
            }
	    return "info/GoodInfoPay";
	}
}
