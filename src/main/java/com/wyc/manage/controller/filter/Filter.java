package com.wyc.manage.controller.filter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.wyc.manage.controller.session.SessionManager;

public abstract class Filter {
	
	private List<Filter> depends;
	
	private SessionManager filterManager;
	
	

	public List<Filter> getDepends() {
		return depends;
	}

	public void setDepends(List<Filter> depends) {
		this.depends = depends;
	}


	

	abstract public Object handlerBefore(SessionManager filterManager)throws Exception;
	
	abstract public Object handlerAfter(SessionManager filterManager)throws Exception;
	
	
	abstract public Object handlerPrivateException(SessionManager filterManager,Method method,Exception e);
	
	abstract public Object handlerPublicException(SessionManager filterManager,Exception e);
	public String toUrl(String url,Map<String, Object> attribute){
		this.filterManager.setEnd(true);
		this.filterManager.setReturnValue(url);
		this.filterManager.setReturnAttribute(attribute);
		
		return url;
	}
	
	
	
	abstract public List<Class<? extends Filter>> dependClasses();
}
