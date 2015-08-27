package com.wyc.controller.api;
import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.City;
import com.wyc.domain.Customer;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CityService;
import com.wyc.service.CustomerService;
import com.wyc.wx.domain.UserInfo;
@RestController
public class BaseApi {
        @Autowired
        private CityService cityService;
        @Autowired
        private CustomerService customerService;
	@RequestMapping(value = "/api/test")
	public String test(HttpServletRequest servletRequest)throws Exception {
		String signature = servletRequest.getParameter("signature");
		String timestamp = servletRequest.getParameter("timestamp");
		String nonce = servletRequest.getParameter("nonce");
		String echostr = servletRequest.getParameter("echostr");
		System.out.println("...........................get........................................");
		System.out.println(signature);
		System.out.println(timestamp);
		System.out.println(nonce);
		System.out.println(echostr);
		System.out.println("............................post........................................");
		try {
		    SAXBuilder saxBuilder = new SAXBuilder();
	                Document document = saxBuilder.build(servletRequest.getInputStream());
	                System.out.println(document.toString());
	                
	                XMLOutputter xmlOutputter = new XMLOutputter();
	                xmlOutputter.output(document, System.out);
                } catch (Exception e) {
                    System.out.println("。。。。。。出了点小问题。。。。。。。");
                }
		
		return echostr;

	}
	@RequestMapping(value = "/api/get_city_by_parentid")
	public Object getCityByParentId(HttpServletRequest httpServletRequest){
	    String parentId = httpServletRequest.getParameter("parent_id");
	    Iterable<City> cities = cityService.findAllByParentId(parentId);
	    return cities;
	}
	
	@UserInfoFromWebAnnotation
	@RequestMapping(value="/api/set_default_address")
	public Object setDefaultAddress(HttpServletRequest httpServletRequest){
	    String addressId = httpServletRequest.getParameter("address_id");
	    MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
	    UserInfo userInfo = myHttpServletRequest.getUserInfo();
	    Customer customer = customerService.findByOpenId(userInfo.getOpenid());
	    customer.setDefaultAddress(addressId);
	    customerService.save(customer);
	    return "{success:true}";
	}
}
