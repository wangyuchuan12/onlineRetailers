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

import com.wyc.annotation.AccessTokenAnnotation;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.Customer;
import com.wyc.domain.CustomerAddress;
import com.wyc.domain.Good;
import com.wyc.domain.GoodImg;
import com.wyc.domain.MyResource;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerAddressService;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodImgService;
import com.wyc.service.GoodService;
import com.wyc.service.MyResourceService;
import com.wyc.wx.domain.UserInfo;
@Controller
public class GoodsAction {
        @Autowired
        private MyResourceService resourceService;
        @Autowired
        private GoodService goodService;
        @Autowired
        private GoodImgService goodImgService;
        @Autowired
        private CustomerService customerService;
        @Autowired
        private CustomerAddressService customerAddressService;
        final static Logger logger = LoggerFactory.getLogger(GoodsAction.class);
	@RequestMapping("/main/good_list")
	@AccessTokenAnnotation
	public String goodList(HttpServletRequest httpRequest){
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
	
	@UserInfoFromWebAnnotation
	@RequestMapping("/info/good_info")
	public String goodInfo(HttpServletRequest httpRequest){
	    MyHttpServletRequest  myHttpServletRequest = (MyHttpServletRequest)httpRequest;
	    String goodId = httpRequest.getParameter("id");
	    
	    Good good = goodService.findOne(goodId);
	    logger.debug("get the good object is {}",good);
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
            List<String> resourceIds = new ArrayList<>();
            Iterable<GoodImg> iterable = goodImgService.findAllByGoodIdOrderByLevel(goodId);
            for(GoodImg goodImg:iterable){
                resourceIds.add(goodImg.getImgId());
            }
            Iterable<MyResource> myResources = resourceService.findAll(resourceIds);
            httpRequest.setAttribute("good", responseGood);
            httpRequest.setAttribute("imgs", myResources);
            httpRequest.setAttribute("token", myHttpServletRequest.getToken());
            logger.debug("the token is :{}",myHttpServletRequest.getToken());
            return "info/GoodInfo";
	}
	
	@UserInfoFromWebAnnotation
	@RequestMapping("/info/good_info_pay")
	public String goodInfoPay(HttpServletRequest httpRequest){
	    MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpRequest;
	    UserInfo userInfo = myHttpServletRequest.getUserInfo();
	    Customer customer = customerService.findByOpenId(userInfo.getOpenid());
	    String defaultAddress = customer.getDefaultAddress();
	    CustomerAddress customerAddress = customerAddressService.findOne(defaultAddress);
	    if(customerAddress==null){
	        int addressCount = customerAddressService.countByCustomerId(customer.getId());
	        if(addressCount==0){
	            return "redirect:/info/address_add?to_redirect=/info/good_info_pay&token="+myHttpServletRequest.getToken().getId();
	        }else{
	            return "redirect:/info/address?to_redirect=/info/good_info_pay&token="+myHttpServletRequest.getToken().getId();
	        }
	    }
	    logger.debug(httpRequest.getParameter("state"));
	    String payType=httpRequest.getParameter("pay_type");
	    if(payType==null){
	        payType = httpRequest.getParameter("state");
	    }
	    String goodId = httpRequest.getParameter("good_id");
	    Good good = goodService.findOne(goodId);
	    MyResource myResource = resourceService.findOne(good.getHeadImg());
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
            responseGood.put("head_img", myResource.getUrl());
            //0表示团购，1表示单独买，2表示开团劵
            if(payType.equals("0")){
                responseGood.put("cost",good.getFlowPrice()+good.getGroupDiscount()*good.getGroupOriginalCost());
            }else if (payType.equals("1")) {
                responseGood.put("cost",good.getFlowPrice()+good.getAloneDiscount()*good.getAloneOriginalCost());
            }else if (payType.equals("2")) {
                responseGood.put("cost",good.getFlowPrice());
            }
            httpRequest.setAttribute("payGoodInfo", responseGood);
	    return "info/GoodInfoPay";
	}
}
