package com.wyc.manage.controller.filter;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.net.httpserver.HttpsConfigurator;
import com.wyc.config.InterceptConfig;
import com.wyc.manage.controller.session.SessionManager;
import com.wyc.manage.domain.vo.WxConfigBean;
import com.wyc.wx.domain.JsapiTicketBean;
import com.wyc.wx.domain.WxContext;

public class WxConfigFilter extends Filter{

	
	final static Logger logger = LoggerFactory.getLogger(WxConfigFilter.class);
	
	@Autowired
	private WxContext wxContext;
	@Override
	public Object handlerBefore(SessionManager filterManager) throws Exception {
		
		
		JsapiTicketBean jsapiTicketBean = (JsapiTicketBean)filterManager.getObject(JsapiTicketBean.class);
		HttpServletRequest httpServletRequest = filterManager.getHttpServletRequest();
		MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
        String datetime = String.valueOf(System.currentTimeMillis() / 1000);
       
        StringBuffer decript = new StringBuffer();
        String url = null;
        if(httpServletRequest.getQueryString()!=null&&!httpServletRequest.getQueryString().toLowerCase().equals("null")){
            url = httpServletRequest.getRequestURL().toString()+"?"+httpServletRequest.getQueryString();
        }else{
            url = httpServletRequest.getRequestURL().toString();
        }
        logger.debug("config url is {}",url);
        String noncestr = "Wm3WZYTPz0wzccnW"+Math.random();
        decript.append("jsapi_ticket=");
        decript.append(jsapiTicketBean.getTicket()+"&");
        decript.append("noncestr=");
        decript.append(noncestr+"&");
        decript.append("timestamp=");
        decript.append(datetime+"&");
        decript.append("url=");
        decript.append(url);
    
        logger.debug("decript:{}",decript);
        logger.debug("jsapi_ticket:{}",jsapiTicketBean.getTicket());
        logger.debug("noncestr:{}",noncestr);
        logger.debug("datetime:{}",datetime);
        logger.debug("url:{}",url);
        digest.reset();
        digest.update(decript.toString().getBytes());
        byte messageDigest[] = digest.digest();
        StringBuffer digestBuffer = new StringBuffer();
        for(byte b :messageDigest){
            String shaHex = Integer.toHexString(b & 0xFF);
            if (shaHex.length() < 2) {
                digestBuffer.append(0);
            }
            digestBuffer.append(shaHex);
        }
        logger.debug("signature:{}",digestBuffer.toString());
        
        WxConfigBean wxConfigBean = new WxConfigBean();
        httpServletRequest.setAttribute("signature", digestBuffer.toString());
        httpServletRequest.setAttribute("noncestr", noncestr);
        httpServletRequest.setAttribute("appId", wxContext.getAppid());
        httpServletRequest.setAttribute("datetime", datetime);
        wxConfigBean.setAppId(wxContext.getAppid());
        wxConfigBean.setSignature(digestBuffer.toString());
        wxConfigBean.setNoncestr(noncestr);
        wxConfigBean.setAppId(wxContext.getAppid());
        wxConfigBean.setDatetime(datetime);
		return wxConfigBean;
	}

	@Override
	public Object handlerAfter(SessionManager filterManager) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handlerPrivateException(SessionManager filterManager, Method method, Exception e) {
		e.printStackTrace();
		return null;
	}

	@Override
	public Object handlerPublicException(SessionManager filterManager, Exception e) {
		e.printStackTrace();
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> list = new ArrayList<>();
		list.add(JsapiTicketFilter.class);
		return list;
	}

}
