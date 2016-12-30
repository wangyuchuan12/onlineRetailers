package com.wyc.manage.controller.action;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wyc.annotation.HandlerAnnotation2;
import com.wyc.annotation.ParamClassAnnotation2;
import com.wyc.manage.controller.filter.BaseActionFilter;
import com.wyc.manage.controller.filter.BaseApiFilter;
import com.wyc.manage.controller.filter.JsapiTicketFilter;
import com.wyc.manage.controller.filter.UserInfoFilter;
import com.wyc.manage.controller.filter.WxConfigFilter;
import com.wyc.manage.controller.session.SessionManager;
import com.wyc.manage.domain.vo.WxConfigBean;
import com.wyc.wx.domain.JsapiTicketBean;
import com.wyc.wx.domain.UserInfo;


@Controller
@RequestMapping(value="/manager/goods")
public class GoodsController {

	
	@ResponseBody
	@RequestMapping(value="/list")
	@HandlerAnnotation2(hanlerFilter=BaseApiFilter.class)
	public Object list(HttpServletRequest httpServletRequest){
		try {
			SessionManager filterManager = SessionManager.getFilterManager(httpServletRequest);
			UserInfo userInfo = (UserInfo) filterManager.getObject(UserInfo.class);
			
			return userInfo;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "manager/Goods";
	}
	
	@ResponseBody
	@RequestMapping(value="/list2")
	@HandlerAnnotation2(hanlerFilter=BaseActionFilter.class)
	public Object list2(HttpServletRequest httpServletRequest){
		try {
			SessionManager filterManager = SessionManager.getFilterManager(httpServletRequest);
			UserInfo userInfo = (UserInfo) filterManager.getObject(UserInfo.class);
			
			return userInfo;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "manager/Goods";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/count")
	public Object count(HttpServletRequest httpServletRequest)throws Exception{
		Map<String, Object> map = new HashMap<>();
		SessionManager filterManager = SessionManager.getFilterManager(httpServletRequest);
		map.put("request", filterManager.getAllRequestAttributes());
		map.put("session", filterManager.getAllSessionAttributes());
		map.put("context", filterManager.getAllContextAttributes());
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/test")
	public Object test(HttpServletRequest httpServletRequest)throws Exception{
		UserInfo userInfo = new UserInfo();
		userInfo.setCity("china");
		userInfo.setCountry("中国");
		SessionManager sessionManager = SessionManager.getFilterManager(httpServletRequest);
		sessionManager.save(userInfo);
		
		userInfo = (UserInfo)sessionManager.getObject(UserInfo.class);
		
		System.out.println(userInfo.getCity());
		
		
		sessionManager.save(userInfo);
		
		
		userInfo = (UserInfo)sessionManager.getObject(UserInfo.class);
		System.out.println(userInfo.getCity());
		return userInfo;
	}
	
}
