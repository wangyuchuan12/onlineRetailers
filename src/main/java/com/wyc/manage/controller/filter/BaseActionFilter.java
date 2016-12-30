package com.wyc.manage.controller.filter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.manage.controller.session.SessionManager;
import com.wyc.wx.domain.UserInfo;
import com.wyc.wx.domain.WxContext;

public class BaseActionFilter extends Filter{

	@Autowired
	private WxContext wxContext;
	
	final static Logger logger = LoggerFactory.getLogger(BaseActionFilter.class);
	@Override
	public Object handlerBefore(SessionManager filterManager) throws Exception {
		UserInfo userInfo = (UserInfo)filterManager.getObject(UserInfo.class);
		HttpServletRequest httpServletRequest = filterManager.getHttpServletRequest();
		if(userInfo==null){
            String requestUrl = httpServletRequest.getRequestURL().toString();
            StringBuffer urlBuffer = new StringBuffer();
            urlBuffer.append(requestUrl);
            java.util.Map<String, String[]> paramMap = httpServletRequest.getParameterMap();
            StringBuffer sb = new StringBuffer();
            sb.append("the parameter is ");
            for(Entry<String, String[]> entry:paramMap.entrySet()){
                if(entry.getValue()!=null&&entry.getValue().length>0){
                    sb.append("&");
                    sb.append(entry.getKey());
                    sb.append("=");
                    sb.append(entry.getValue()[0]);
                }
            }
            
            if(paramMap.entrySet().size()>0){
                urlBuffer.append("?");
            }
            for(Entry<String, String[]> entry:paramMap.entrySet()){
                if(entry.getValue()!=null&&entry.getValue().length>0){
                    urlBuffer.append("&"+entry.getKey()+"="+entry.getValue()[0]);
                }
            }
            if(urlBuffer.toString().contains("&")){
                urlBuffer.deleteCharAt(urlBuffer.indexOf("&"));
            }
            
            String wxRequestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
            "appid="+wxContext.getAppid()+"&redirect_uri="+urlBuffer.toString()+"&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect";
            logger.debug("redirect to url [{}]",wxRequestUrl);
            
            filterManager.setEnd(true);
            filterManager.setReturnValue(wxRequestUrl);
            return null;
        }
		return null;
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
		List<Class<? extends Filter>> filters = new ArrayList<>();
		filters.add(UserInfoFilter.class);
		return filters;
	}

}
