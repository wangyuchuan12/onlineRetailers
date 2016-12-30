package com.wyc.manage.controller.filter;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.annotation.ParamEntityAnnotation2;
import com.wyc.manage.controller.session.SessionManager;
import com.wyc.smart.service.WxJsApiTicketSmartService;
import com.wyc.wx.domain.JsapiTicketBean;



public class JsapiTicketFilter extends Filter{
	@Autowired
    private WxJsApiTicketSmartService wxJsApiTicketSmartService;

	@Override
	public Object handlerBefore(SessionManager filterManager) throws Exception {
		JsapiTicketBean jsapiTicketBean = wxJsApiTicketSmartService.getFromDatabase();
		
		if(jsapiTicketBean==null){
            jsapiTicketBean = wxJsApiTicketSmartService.getFromWx();
            jsapiTicketBean = wxJsApiTicketSmartService.addToDataBase(jsapiTicketBean);
        }
        if(!wxJsApiTicketSmartService.currentIsAvailable()){
            String id = jsapiTicketBean.getId();
            jsapiTicketBean = wxJsApiTicketSmartService.getFromWx();
            jsapiTicketBean.setId(id);
            jsapiTicketBean = wxJsApiTicketSmartService.saveToDataBase(jsapiTicketBean);
        }
		return jsapiTicketBean;
	}

	@Override
	public Object handlerAfter(SessionManager filterManager) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handlerPrivateException(SessionManager filterManager, Method method, Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handlerPublicException(SessionManager filterManager, Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		// TODO Auto-generated method stub
		return null;
	}
}
