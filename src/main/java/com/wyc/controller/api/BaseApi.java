package com.wyc.controller.api;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.SystemCity;
import com.wyc.domain.Customer;
import com.wyc.domain.CustomerAddress;
import com.wyc.domain.TemporaryData;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CityService;
import com.wyc.service.CustomerAddressService;
import com.wyc.service.CustomerService;
import com.wyc.service.TemporaryDataService;
import com.wyc.wx.domain.UserInfo;
@RestController
public class BaseApi {
        @Autowired
        private CityService cityService;
        @Autowired
        private CustomerService customerService;
        @Autowired
        private CustomerAddressService customerAddressService;
        @Autowired
        private TemporaryDataService temporaryDataService;
        final static Logger logger = LoggerFactory.getLogger(BaseApi.class);
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
	
	@RequestMapping(value = "/api/set_temporary_data")
	public Object setTemporaryData(HttpServletRequest httpServletRequest){
	    String key = httpServletRequest.getParameter("key");
	    String name = httpServletRequest.getParameter("name");
	    String value = httpServletRequest.getParameter("value");
	    TemporaryData temporaryData = new TemporaryData();
	    temporaryData.setKeyName("setTemporaryData");
	    temporaryData.setMykey(key);
	    temporaryData.setName(name);
	    temporaryData.setValue(value);
	    temporaryData = temporaryDataService.add(temporaryData);
	    Map<String, Object> responseData = new HashMap<String, Object>();
	    responseData.put("success", true);
	    responseData.put("id",temporaryData.getId());
	    
	    return responseData;
	}
	
	@RequestMapping(value = "/api/get_temporary_data")
	public Object getTemporaryData(HttpServletRequest httpServletRequest){
	    String key = httpServletRequest.getParameter("key");
            String name = httpServletRequest.getParameter("name");
            TemporaryData temporaryData = temporaryDataService.findByMyKeyAndNameAndStatus(key, name ,1);
            Map<String, String> map = new HashMap<String, String>();
            map.put(temporaryData.getName(), temporaryData.getValue());
            return map;
	}
	
	@RequestMapping(value = "/api/get_city_by_parentid")
	public Object getCityByParentId(HttpServletRequest httpServletRequest){
	    String parentId = httpServletRequest.getParameter("parent_id");
	    Iterable<SystemCity> cities = cityService.findAllByParentId(parentId);
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
	
	@UserInfoFromWebAnnotation
        @RequestMapping(value="/api/add_address")
        public Object addAddress(HttpServletRequest httpServletRequest){
	    MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
	        UserInfo userInfo = myHttpServletRequest.getUserInfo();
	        Customer customer = customerService.findByOpenId(userInfo.getOpenid());
	        String cityId = myHttpServletRequest.getParameter("city_id");
	        String type = myHttpServletRequest.getParameter("type");
	        String content = myHttpServletRequest.getParameter("content");
	        String id = myHttpServletRequest.getParameter("id");
	        String name = myHttpServletRequest.getParameter("name");
	        String phonenumber = myHttpServletRequest.getParameter("phonenumber");
	        CustomerAddress customerAddress = null;
	        if(id!=null){
	            customerAddress = customerAddressService.findOne(id);
	        }
	        if(customerAddress==null){
	                customerAddress = new CustomerAddress();
	                customerAddress.setName(name);
	                customerAddress.setPhonenumber(phonenumber);
	                customerAddress.setCity(cityId);
	                customerAddress.setContent(content);
	                customerAddress.setCustomerId(customer.getId());
	                customerAddress.setType(Integer.parseInt(type));
	                customerAddress = customerAddressService.add(customerAddress);
	                customer.setDefaultAddress(customerAddress.getId());
	                customerService.save(customer);
	        }else{
	            customerAddress.setName(name);
	            customerAddress.setPhonenumber(phonenumber);
	            customerAddress.setCity(cityId);
	            customerAddress.setContent(content);
	            customerAddress.setCustomerId(customer.getId());
	            customerAddress.setType(Integer.parseInt(type));
	            customerAddressService.save(customerAddress);
	        }
            return "{success:true}";
        }
}
