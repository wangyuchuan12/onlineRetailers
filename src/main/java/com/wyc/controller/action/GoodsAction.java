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
import com.wyc.annotation.BeforeNativeHandlerAnnotation;
import com.wyc.annotation.JsApiTicketAnnotation;
import com.wyc.annotation.NowPageRecordAnnotation;
import com.wyc.annotation.ReturnUrl;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.annotation.WxChooseWxPay;
import com.wyc.annotation.WxConfigAnnotation;
import com.wyc.annotation.handler.AfterGoodTypeHandler;
import com.wyc.annotation.handler.BeforeGoodTypeHandler;
import com.wyc.annotation.handler.NotReadChatHandler;
import com.wyc.annotation.handler.PayResultHandler;
import com.wyc.annotation.handler.WxChooseWxPayHandler;
import com.wyc.domain.GoodStyle;
import com.wyc.domain.SystemAdGoodHeaderImg;
import com.wyc.domain.SystemCity;
import com.wyc.domain.Customer;
import com.wyc.domain.CustomerAddress;
import com.wyc.domain.Good;
import com.wyc.domain.GoodImg;
import com.wyc.domain.SystemGoodType;
import com.wyc.domain.MyResource;
import com.wyc.domain.SystemQuickEntrance;
import com.wyc.domain.TempGroupOrder;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.AdGoodHeaderImgService;
import com.wyc.service.CityService;
import com.wyc.service.CustomerAddressService;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodImgService;
import com.wyc.service.GoodService;
import com.wyc.service.GoodStyleService;
import com.wyc.service.GoodTypeService;
import com.wyc.service.MyResourceService;
import com.wyc.service.OpenGroupCouponService;
import com.wyc.service.QuickEntranceService;
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
        @Autowired
        private AdGoodHeaderImgService adGoodHeaderImgService;
        @Autowired
        private QuickEntranceService quickEntranceService;
        @Autowired
        private GoodStyleService goodStyleService;
        final static Logger logger = LoggerFactory.getLogger(GoodsAction.class);
	@RequestMapping("/main/good_list")
	@AccessTokenAnnotation
	@UserInfoFromWebAnnotation
	@JsApiTicketAnnotation
	@WxConfigAnnotation
	@NowPageRecordAnnotation(page=0)
	@AfterHandlerAnnotation(hanlerClasses={NotReadChatHandler.class})
	public String goodList(HttpServletRequest httpRequest)throws Exception{  
	    MyHttpServletRequest  myHttpServletRequest = (MyHttpServletRequest)httpRequest;
	    UserInfo userInfo = myHttpServletRequest.getUserInfo();
	    String goodTypeId = myHttpServletRequest.getParameter("good_type");
	    Customer customer = customerService.findByOpenId(userInfo.getOpenid());
	    if(goodTypeId==null||goodTypeId.trim().equals("")){
	        goodTypeId = customer.getDefaultGoodType();
	        if(goodTypeId==null||goodTypeId.trim().equals("")){
	                Iterable<SystemGoodType> goodTypeIterable = goodTypeService.findAll();
	                for(SystemGoodType goodTypeEntity:goodTypeIterable){
	                    if(goodTypeEntity.isDefault()){
	                        goodTypeId = goodTypeEntity.getId();
	                        break;
	                    }
	                    goodTypeId = goodTypeEntity.getId();
	                }
	                customer.setDefaultGoodType(goodTypeId);
	                customerService.save(customer);
	        }
	    }else{
	        customer.setDefaultGoodType(goodTypeId);
	        customerService.save(customer);
	    }
	    
	    SystemGoodType goodType = goodTypeService.findOne(goodTypeId);
	    if(goodType==null){
	        Iterable<SystemGoodType> goodTypeIterable = goodTypeService.findAll();
                for(SystemGoodType goodTypeEntity:goodTypeIterable){
                    if(goodTypeEntity.isDefault()){
                        goodTypeId = goodTypeEntity.getId();
                        goodType = goodTypeEntity;
                        break;
                    }
                    goodTypeId = goodTypeEntity.getId();
                    goodType = goodTypeEntity;
                }
	    }
	    
	    Iterable<SystemAdGoodHeaderImg> adGoodHeaderImgs = adGoodHeaderImgService.findAllOrderByRankAsc();
	    
	    Iterable<Good> databaseGoods = goodService.findAllByGoodTypeAndIsDisplayMainOrderByRank(goodTypeId,true);
            Iterable<SystemQuickEntrance> quickEntrances = quickEntranceService.findAllOrderByRankAsc();
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
		    responseGood.put("notice", good.getNotice());
		    responseGood.put("stock", good.getStock());
		    responseGood.put("salesVolume", good.getSalesVolume());
		    responseGood.put("adminId", good.getAdminId());
		    MyResource myResource = resourceService.findOne(good.getHeadImg());
		    if(myResource!=null){
		        responseGood.put("head_img", myResource.getUrl());
		    }
		    responseGoods.add(responseGood);
		}
               httpRequest.setAttribute("adGoodHeaderImgs", adGoodHeaderImgs);
               httpRequest.setAttribute("quickEntrances", quickEntrances);
	       httpRequest.setAttribute("goods", responseGoods);
	       httpRequest.setAttribute("goodType", goodTypeId);
	       if(goodTypeId!=null){
        	       
        	       httpRequest.setAttribute("typeTitle", goodType.getTitle());
        	       httpRequest.setAttribute("typeName", goodType.getName());
        	       httpRequest.setAttribute("typeImg", goodType.getImg());
	       }
	       return "main/Goods";
	}
	
	@UserInfoFromWebAnnotation
	@JsApiTicketAnnotation
	@RequestMapping("/info/good_info")
	@NowPageRecordAnnotation(page=1)
	@WxConfigAnnotation
	@BeforeNativeHandlerAnnotation(hanlerClasses={BeforeGoodTypeHandler.class})
	@AfterHandlerAnnotation(hanlerClasses={AfterGoodTypeHandler.class,NotReadChatHandler.class})
	public String goodInfo(HttpServletRequest httpRequest)throws Exception{
	    MyHttpServletRequest  myHttpServletRequest = (MyHttpServletRequest)httpRequest;
	    UserInfo userInfo = myHttpServletRequest.getUserInfo();
            
           
	    String goodId = httpRequest.getParameter("id");
	    logger.debug("get js api tick is {}",myHttpServletRequest.getJsapiTicketBean().getTicket());
	    Good good = goodService.findOne(goodId);
	    Customer customer = customerService.findByOpenId(userInfo.getOpenid());
	    customer.setDefaultGoodType(good.getGoodType());
	    customerService.save(customer);
	    logger.debug("get the good object is {}",good);
	    myHttpServletRequest.setAttribute("goodType", good.getGoodType());
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
            responseGood.put("goodInfoHeadImg", good.getGoodInfoHeadImg());
            responseGood.put("adminId", good.getAdminId());
            responseGood.put("stock", good.getStock());
            responseGood.put("salesVolume", good.getSalesVolume());
            responseGood.put("status", good.getStatus());
            MyResource myResource = resourceService.findOne(good.getHeadImg());
            MyResource goodInfoHeadImgResource = resourceService.findOne(good.getGoodInfoHeadImg());
            if(myResource!=null){
                responseGood.put("head_img", myResource.getUrl());
            }
            
            if(goodInfoHeadImgResource!=null){
                responseGood.put("goodInfoHeadImg", goodInfoHeadImgResource.getUrl());
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
	@BeforeNativeHandlerAnnotation(hanlerClasses={BeforeGoodTypeHandler.class})
    @AfterHandlerAnnotation(hanlerClasses={AfterGoodTypeHandler.class,NotReadChatHandler.class})
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
            SystemCity city = null;
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
            
            responseGood.put("stock", good.getStock());
            responseGood.put("salesVolume", good.getSalesVolume());
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
            tempGroupOrder.setAdminId(good.getAdminId());
            tempGroupOrder.setPersonName(customerAddress.getName());
            tempGroupOrder.setPhonenumber(customerAddress.getPhonenumber());
            if(payType.equals("3")){
                tempGroupOrder.setGroupId(httpRequest.getParameter("group_id"));
            }
            tempGroupOrder = tempGroupOrderService.add(tempGroupOrder);
            
            Iterable<GoodStyle> goodStyles = goodStyleService.findAllByGoodId(good.getId());
            httpRequest.setAttribute("goodStyles", goodStyles);
            httpRequest.setAttribute("tempGroupOrderId", tempGroupOrder.getId());
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
