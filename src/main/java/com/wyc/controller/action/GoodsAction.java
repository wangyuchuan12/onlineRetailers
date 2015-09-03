package com.wyc.controller.action;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyc.annotation.AccessTokenAnnotation;
import com.wyc.annotation.JsApiTicketAnnotation;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.City;
import com.wyc.domain.Customer;
import com.wyc.domain.CustomerAddress;
import com.wyc.domain.Good;
import com.wyc.domain.GoodImg;
import com.wyc.domain.MyResource;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CityService;
import com.wyc.service.CustomerAddressService;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodImgService;
import com.wyc.service.GoodService;
import com.wyc.service.MyResourceService;
import com.wyc.service.OpenGroupCouponService;
import com.wyc.wx.domain.UserInfo;
import com.wyc.wx.domain.WxContext;
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
        @Autowired
        private CityService cityService;
        @Autowired
        private OpenGroupCouponService openGroupCouponService;
        @Autowired
        private WxContext wxContext;
        final static Logger logger = LoggerFactory.getLogger(GoodsAction.class);
	@RequestMapping("/main/good_list")
	@AccessTokenAnnotation
	@JsApiTicketAnnotation
	public String goodList(HttpServletRequest httpRequest)throws Exception{
	        MyHttpServletRequest  myHttpServletRequest = (MyHttpServletRequest)httpRequest;
	        Iterable<Good> databaseGoods = goodService.findAll();
		List<Map<String, Object>> responseGoods = new ArrayList<Map<String, Object>>();
		
		 MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
	            long datetime = new Date().getTime();
	            logger.debug("datetime:{}",datetime);
	            String decript = "jsapi_ticket=kgt8ON7yVITDhtdwci0qeS9Tjc5DK9ogBGC5AD_PDIjAZKpeFFyN2eSQpKB5zADm2MAvbLmcScC52E7KGzqNTg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1441253227&url=http://www.chengxi.pub/main/good_list";
	            digest.reset();
	            digest.update(decript.getBytes());
	            byte messageDigest[] = digest.digest();
	            StringBuffer sb = new StringBuffer();
	            for(byte b :messageDigest){
	                String shaHex = Integer.toHexString(b & 0xFF);
	                if (shaHex.length() < 2) {
	                    sb.append(0);
	                }
	                sb.append(shaHex);
	            }
	            logger.debug("signature:{}",sb.toString());
	            httpRequest.setAttribute("signature", sb.toString());
	            httpRequest.setAttribute("noncestr", "Wm3WZYTPz0wzccnW");
	            httpRequest.setAttribute("appId", wxContext.getAppid());
		
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
	@JsApiTicketAnnotation
	@RequestMapping("/info/good_info")
	public String goodInfo(HttpServletRequest httpRequest)throws Exception{
	    MyHttpServletRequest  myHttpServletRequest = (MyHttpServletRequest)httpRequest;
	    UserInfo userInfo = myHttpServletRequest.getUserInfo();
	    Formatter formatter = new Formatter();
            Customer customer = customerService.findByOpenId(userInfo.getOpenid());
           
	    String goodId = httpRequest.getParameter("id");
	    logger.debug("get js api tick is {}",myHttpServletRequest.getJsapiTicketBean().getTicket());
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
            int couponCount = openGroupCouponService.countByCustomerIdAndGoodIdAndEndTimeBeforeAndStatus(customer.getId(), goodId, new DateTime(),1);
            httpRequest.setAttribute("couponCount", couponCount);
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
	            return "redirect:/info/address_add?prepare_redirect=/info/good_info_pay?state="+httpRequest.getParameter("state")+"&pay_type="+httpRequest.getParameter("pay_type")+"&good_id="+httpRequest.getParameter("good_id")+"&token="+myHttpServletRequest.getToken().getId();
	        }else{
	            return "redirect:/info/address?prepare_redirect=/info/good_info_pay?state="+httpRequest.getParameter("state")+"&pay_type="+httpRequest.getParameter("pay_type")+"&good_id="+httpRequest.getParameter("good_id")+"&token="+myHttpServletRequest.getToken().getId();
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
            StringBuffer citySb = new StringBuffer();
            String cityId = customerAddress.getCity();
            City city = null;
            while((city=cityService.findOne(cityId))!=null){
                cityId = city.getParentId();
                citySb.insert(0, city.getName());
            }
            citySb.append(customerAddress.getContent());
            responseGood.put("id", good.getId());
            responseGood.put("good_id", goodId);
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
            responseGood.put("person_name", customerAddress.getName());
            responseGood.put("phonenumber", customerAddress.getPhonenumber());
            responseGood.put("address", citySb.toString());
            responseGood.put("address_id", customerAddress.getId());
            responseGood.put("group_cost", good.getGroupDiscount()*good.getGroupOriginalCost());
            responseGood.put("alone_cost", good.getAloneDiscount()*good.getAloneOriginalCost());
            responseGood.put("pay_type", payType);
            responseGood.put("head_img", myResource.getUrl());
            //0琛ㄧず鍥㈣喘锛�1琛ㄧず鍗曠嫭涔帮紝2琛ㄧず寮�鍥㈠姷
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
