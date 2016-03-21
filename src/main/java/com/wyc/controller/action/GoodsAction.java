package com.wyc.controller.action;
import java.util.ArrayList;
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
import com.wyc.annotation.AfterHandlerAnnotation;
import com.wyc.annotation.BeforeHandlerAnnotation;
import com.wyc.annotation.JsApiTicketAnnotation;
import com.wyc.annotation.NowPageRecordAnnotation;
import com.wyc.annotation.ReturnUrl;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.annotation.WxChooseWxPay;
import com.wyc.annotation.WxConfigAnnotation;
import com.wyc.annotation.handler.PayResultHandler;
import com.wyc.annotation.handler.WxChooseWxPayHandler;
import com.wyc.domain.City;
import com.wyc.domain.Customer;
import com.wyc.domain.CustomerAddress;
import com.wyc.domain.Good;
import com.wyc.domain.GoodImg;
import com.wyc.domain.MyResource;
import com.wyc.domain.TempGroupOrder;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CityService;
import com.wyc.service.CustomerAddressService;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodImgService;
import com.wyc.service.GoodService;
import com.wyc.service.GoodTypeService;
import com.wyc.service.MyResourceService;
import com.wyc.service.OpenGroupCouponService;
import com.wyc.service.TempGroupOrderService;
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
        @Autowired
        private TempGroupOrderService tempGroupOrderService;
        @Autowired
        private GoodTypeService goodTypeService;
        final static Logger logger = LoggerFactory.getLogger(GoodsAction.class);
	@RequestMapping("/main/good_list")
	@AccessTokenAnnotation
	@JsApiTicketAnnotation
	@UserInfoFromWebAnnotation
	@WxConfigAnnotation
	@NowPageRecordAnnotation(page=0)
	public String goodList(HttpServletRequest httpRequest)throws Exception{  
	    MyHttpServletRequest  myHttpServletRequest = (MyHttpServletRequest)httpRequest;
	    String goodTypeId = myHttpServletRequest.getAttribute("goodType").toString();
	    Iterable<Good> databaseGoods = goodService.findAllByStatusAndGoodTypeOrderByRank(1,goodTypeId);
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
		    responseGood.put("title", good.getTitle());
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
	@NowPageRecordAnnotation(page=1)
	@WxConfigAnnotation
	public String goodInfo(HttpServletRequest httpRequest)throws Exception{
	    MyHttpServletRequest  myHttpServletRequest = (MyHttpServletRequest)httpRequest;
	    UserInfo userInfo = myHttpServletRequest.getUserInfo();
            Customer customer = customerService.findByOpenId(userInfo.getOpenid());
           
	    String goodId = httpRequest.getParameter("id");
	    logger.debug("get js api tick is {}",myHttpServletRequest.getJsapiTicketBean().getTicket());
	    Good good = goodService.findOne(goodId);
	    logger.debug("get the good object is {}",good);
	    Map<String, Object> responseGood = new HashMap<String, Object>();
            responseGood.put("id", good.getId());
            responseGood.put("instruction", good.getInstruction());
            responseGood.put("title", good.getTitle());
            responseGood.put("name", good.getName());
            responseGood.put("head_img", good.getHeadImg());
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
	@WxChooseWxPay
	@JsApiTicketAnnotation
	@WxConfigAnnotation
	@BeforeHandlerAnnotation(hanlerClasses={WxChooseWxPayHandler.class})
	@RequestMapping("/info/good_info_pay")
	@NowPageRecordAnnotation(page=2)
	public String goodInfoPay(HttpServletRequest httpRequest){
	    MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpRequest;
	    UserInfo userInfo = myHttpServletRequest.getUserInfo();
	    Customer customer = customerService.findByOpenId(userInfo.getOpenid());
	    String defaultAddress = customer.getDefaultAddress();
	    CustomerAddress customerAddress = null;
	    if(defaultAddress!=null&&!defaultAddress.trim().equals("")){
	        customerAddress = customerAddressService.findOne(defaultAddress);
	    }
	    
	    if(customerAddress==null){
	        int addressCount = customerAddressService.countByCustomerId(customer.getId());
	        if(addressCount==0){
	            String url = "redirect:/info/address_add?prepare_redirect=/info/good_info_pay?state="+httpRequest.getParameter("state")+"&pay_type="+httpRequest.getParameter("pay_type")+"&good_id="+httpRequest.getParameter("good_id")+"&token="+myHttpServletRequest.getToken().getId()+"&group_id="+myHttpServletRequest.getParameter("group_id");
	            System.out.println(".............url:"+url);
	            return url;
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
                citySb.insert(0, city.getName()+"-");
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
            responseGood.put("cost", httpRequest.getAttribute("cost"));
            httpRequest.setAttribute("payGoodInfo", responseGood);
            
            TempGroupOrder tempGroupOrder = new TempGroupOrder();
            tempGroupOrder.setOutTradeNo(httpRequest.getAttribute("outTradeNo").toString());
            tempGroupOrder.setAddress(citySb.toString());
            tempGroupOrder.setAddressId(customerAddress.getId());
            tempGroupOrder.setCode(httpRequest.getAttribute("outTradeNo").toString());
            tempGroupOrder.setCost(Float.parseFloat(httpRequest.getAttribute("cost").toString()));
            tempGroupOrder.setCustomerAddress(customerAddress.getId());
            tempGroupOrder.setFlowPrice(good.getFlowPrice());
            tempGroupOrder.setGoodId(goodId);
            tempGroupOrder.setGoodPrice(Float.parseFloat(httpRequest.getAttribute("cost").toString()));
            tempGroupOrder.setNum(good.getGroupNum());
            tempGroupOrder.setOpenid(userInfo.getOpenid());
            tempGroupOrder.setGoodOrderType(Integer.parseInt(payType));
            tempGroupOrder.setTimeLong(good.getTimeLong());
            if(payType.equals("3")){
                tempGroupOrder.setGroupId(httpRequest.getParameter("group_id"));
            }
            tempGroupOrderService.add(tempGroupOrder);
	    return "info/GoodInfoPay";
	}
	
	@UserInfoFromWebAnnotation
	@RequestMapping("/info/pay_failure")
	@AfterHandlerAnnotation(hanlerClasses=PayResultHandler.class)
	@ReturnUrl(url="/info/order_info?id=%orderId%")
	public void payFailure(HttpServletRequest httpServletRequest){
	    MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
	    UserInfo userInfo = myHttpServletRequest.getUserInfo();
	    String goodId = myHttpServletRequest.getParameter("good_id");
	    String payType = myHttpServletRequest.getParameter("pay_type");
	    String orderId = myHttpServletRequest.getParameter("order_id");
	    String userId = userInfo.getId();
	    String openId = userInfo.getOpenid();
	    httpServletRequest.setAttribute("good_id",goodId);
            httpServletRequest.setAttribute("pay_type",payType);
            httpServletRequest.setAttribute("openId",openId);
            httpServletRequest.setAttribute("userId",userId);
            httpServletRequest.setAttribute("status", 1);
            httpServletRequest.setAttribute("orderId", orderId);
	}
}
