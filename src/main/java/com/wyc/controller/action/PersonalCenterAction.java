package com.wyc.controller.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.defineBean.ApplicationProperties;
import com.wyc.defineBean.MySimpleDateFormat;
import com.wyc.domain.City;
import com.wyc.domain.Customer;
import com.wyc.domain.CustomerAddress;
import com.wyc.domain.Good;
import com.wyc.domain.OpenGroupCoupon;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CityService;
import com.wyc.service.CustomerAddressService;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodService;
import com.wyc.service.OpenGroupCouponService;
import com.wyc.wx.domain.UserInfo;

@Controller
public class PersonalCenterAction {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerAddressService customerAddressService;
	@Autowired
	private CityService cityService;
	@Autowired
	private ApplicationProperties applicationProperties;
	@Autowired
	private OpenGroupCouponService openGroupCouponService;
	@Autowired
	private MySimpleDateFormat mySimpleDateFormat;
	@Autowired
	private GoodService goodService;
    @RequestMapping("/main/personal_center")
    @UserInfoFromWebAnnotation
    public String personCenter(HttpServletRequest httpServletRequest){
    	MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
    	UserInfo userInfo = myHttpServletRequest.getUserInfo();
    	httpServletRequest.setAttribute("userInfo",userInfo);
        return "main/PersonalCenter";
    }
    
    @RequestMapping("/info/address_add")
    public String addressAdd(HttpServletRequest httpServletRequest){
        String goodDistributions = applicationProperties.getProperty("good_distributions");
        String[] goodDistributionCodes= null;
        if(goodDistributions.contains(",")){
            goodDistributionCodes = goodDistributions.split(",");
        }else{
            goodDistributionCodes = new String[]{goodDistributions};
        }
        List<Map<String, Object>> responseProvinces = new ArrayList<Map<String,Object>>();
        List<City> provinces = new ArrayList<City>();
        for(String goodDistributionCode:goodDistributionCodes){
            Iterable<City> iterable = cityService.findAllByCodeAndType(goodDistributionCode,1);
            for(City city:iterable){
                provinces.add(city);
            }
        }
        for(City provice:provinces){
            Map<String, Object> responseProvince = new HashMap<String, Object>();
            responseProvince.put("id", provice.getId());
            responseProvince.put("name", provice.getName());
            responseProvince.put("code", provice.getCode());
            Iterable<City> citys = cityService.findAllByParentId(provice.getId());
            List<Map<String, Object>> responseCities = new ArrayList<Map<String,Object>>();
            responseProvince.put("cities", responseCities);
            responseProvinces.add(responseProvince);
            for(City city:citys){
                Map<String, Object> responseCity = new HashMap<String, Object>();
                responseCity.put("id", city.getId());
                responseCity.put("name", city.getName());
                responseCity.put("code", city.getCode());
                List<Map<String, String>> responseAddresses = new ArrayList<Map<String,String>>();
                Iterable<City> addresses = cityService.findAllByParentId(city.getId());
                responseCity.put("addresses", responseAddresses);
                responseCities.add(responseCity);
                for(City address:addresses){
                    Map<String, String> responseAddress = new HashMap<String, String>();
                    responseAddress.put("id", address.getId());
                    responseAddress.put("name", address.getName());
                    responseAddress.put("code", address.getCode());
                    responseAddresses.add(responseAddress);
                }
            }
        }
        httpServletRequest.setAttribute("cities", responseProvinces);
        return "info/AddressInfoAdd";
    }
    
    @RequestMapping("/info/address_save")
    public String addressSave(HttpServletRequest httpServletRequest){
        addressAdd(httpServletRequest);
        String addressId = httpServletRequest.getParameter("address_id");
        CustomerAddress customerAddress = customerAddressService.findOne(addressId);
        httpServletRequest.setAttribute("address", customerAddress.getCity());
        City address = cityService.findOne(customerAddress.getCity());
        City city = cityService.findOne(address.getParentId());
        City province = cityService.findOne(city.getParentId());
        httpServletRequest.setAttribute("city", city.getId());
        httpServletRequest.setAttribute("province", province.getId());
        httpServletRequest.setAttribute("content", customerAddress.getContent());
        httpServletRequest.setAttribute("id", customerAddress.getId());
        httpServletRequest.setAttribute("name", customerAddress.getName());
        httpServletRequest.setAttribute("phonenumber", customerAddress.getPhonenumber());
        httpServletRequest.setAttribute("type", customerAddress.getType());
        return "info/AddressInfoAdd";
    }
    
    @RequestMapping("/action/do_address_save")
    @UserInfoFromWebAnnotation
    public String doAddressSave(HttpServletRequest httpServletRequest){
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
    	return address(httpServletRequest);
    }
    @RequestMapping("/info/address")
    @UserInfoFromWebAnnotation
    public String address(HttpServletRequest httpServletRequest){
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        String openid = userInfo.getOpenid();
        Customer customer = customerService.findByOpenId(openid);
        Iterable<CustomerAddress> customerAddresses = customerAddressService.findByCustomerIdOrderByCreateAtDesc(customer.getId());
        List<Map<String, Object>> responseCustomerAddresses = new ArrayList<Map<String,Object>>();
        String defaultAddress = customer.getDefaultAddress();
        for(CustomerAddress customerAddress:customerAddresses){
            Map<String, Object> responseCustomerAddress = new HashMap<String, Object>();
            String addressId = customerAddress.getCity();
            City address = cityService.findOne(addressId);
            City city = cityService.findOne(address.getParentId());
            City province = cityService.findOne(city.getParentId());
            responseCustomerAddress.put("address", address.getName());
            responseCustomerAddress.put("city", city.getName());
            responseCustomerAddress.put("province", province.getName());
            responseCustomerAddress.put("content", customerAddress.getContent());
            responseCustomerAddress.put("id", customerAddress.getId());
            responseCustomerAddress.put("name", customerAddress.getName());
            responseCustomerAddress.put("phonenumber", customerAddress.getPhonenumber());
            responseCustomerAddress.put("type", customerAddress.getType());
            if(customerAddress.getId().equals(defaultAddress)){
                responseCustomerAddress.put("isDefault", true);
            }else{
                responseCustomerAddress.put("isDefault", false);
            }
            responseCustomerAddresses.add(responseCustomerAddress);
        }
        httpServletRequest.setAttribute("addresses", responseCustomerAddresses);
        return "info/Address";
    }
   
    @RequestMapping("/info/coupon")
    @UserInfoFromWebAnnotation
    public String coupon(HttpServletRequest httpServletRequest){
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        Customer customer = customerService.findByOpenId(userInfo.getOpenid());
        Iterable<OpenGroupCoupon> openGroupCoupons = openGroupCouponService.findAllByCustomerId(customer.getId());
        List<Map<String, String>> responseCoupons = new ArrayList<Map<String,String>>();
        for(OpenGroupCoupon openGroupCoupon:openGroupCoupons){
            Map<String, String> responseCoupon = new HashMap<String, String>();
            responseCoupon.put("id", openGroupCoupon.getId());
            responseCoupon.put("customerId", openGroupCoupon.getCustomerId());
            responseCoupon.put("goodId", openGroupCoupon.getGoodId());
            Good good = goodService.findOne(openGroupCoupon.getGoodId());
            responseCoupon.put("goodName", good.getName());
            responseCoupon.put("beginTime", mySimpleDateFormat.format(openGroupCoupon.getBeginTime().toDate()));
            responseCoupon.put("endTime", mySimpleDateFormat.format(openGroupCoupon.getEndTime().toDate()));
            responseCoupon.put("createManager", openGroupCoupon.getCreateManager());
            if(openGroupCoupon.getStatus()==2||openGroupCoupon.getStatus()==0){
                responseCoupon.put("status", openGroupCoupon.getStatus()+"");
            }else if (openGroupCoupon.getStatus()==1) {
                if(openGroupCoupon.getEndTime().toDate().getTime()>new Date().getTime()){
                    openGroupCoupon.setStatus(2);
                    openGroupCouponService.save(openGroupCoupon);
                    responseCoupon.put("status", "2");
                }else{
                    responseCoupon.put("status", "1");
                }
               
            }
            
            responseCoupons.add(responseCoupon);
        }
        httpServletRequest.setAttribute("coupons", responseCoupons);
        return "info/coupon";
    }
    
    @UserInfoFromWebAnnotation
    @RequestMapping(value="/action/set_default_address")
    public String setDefaultAddress(HttpServletRequest httpServletRequest){
        String addressId = httpServletRequest.getParameter("address_id");
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        Customer customer = customerService.findByOpenId(userInfo.getOpenid());
        customer.setDefaultAddress(addressId);
        customerService.save(customer);
        return address(myHttpServletRequest);
    }
}
