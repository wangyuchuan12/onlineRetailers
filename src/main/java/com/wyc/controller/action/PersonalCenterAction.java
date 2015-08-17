package com.wyc.controller.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.Customer;
import com.wyc.domain.CustomerAddress;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerAddressService;
import com.wyc.service.CustomerService;
import com.wyc.wx.domain.UserInfo;

@Controller
public class PersonalCenterAction {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerAddressService customerAddressService;
    @RequestMapping("/main/personal_center")
    @UserInfoFromWebAnnotation
    public String personCenter(HttpServletRequest httpServletRequest){
    	MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
    	UserInfo userInfo = myHttpServletRequest.getUserInfo();
    	httpServletRequest.setAttribute("userInfo",userInfo);
        return "main/PersonalCenter";
    }
    
    @RequestMapping("/info/address_add")
    @UserInfoFromWebAnnotation
    public String addressAdd(HttpServletRequest httpServletRequest){
    	
        return "info/AddressInfo";
    }
    
    public String doAddressAdd(HttpServletRequest httpServletRequest){
    	MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
    	UserInfo userInfo = myHttpServletRequest.getUserInfo();
    	Customer customer = customerService.findByOpenId(userInfo.getOpenid());
    	String cityId = myHttpServletRequest.getParameter("city_id");
    	String type = myHttpServletRequest.getParameter("type");
    	String content = myHttpServletRequest.getParameter("content");
    	CustomerAddress customerAddress = new CustomerAddress();
    	customerAddress.setCity(cityId);
    	customerAddress.setContent(content);
    	customerAddress.setCustomerId(customer.getOpenId());
    	customerAddress.setType(Integer.parseInt(type));
    	customerAddressService.add(customerAddress);
    	return address(httpServletRequest);
    }
    @RequestMapping("/info/address")
    @UserInfoFromWebAnnotation
    public String address(HttpServletRequest httpServletRequest){
        return "info/Address";
    }
   
    @RequestMapping("/info/coupon")
    public String coupon(HttpServletRequest httpServletRequest){
        return "info/coupon";
    }
}
