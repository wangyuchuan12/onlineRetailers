package com.wyc.manage.controller.session;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleIfStatement.Else;
import com.sun.net.httpserver.HttpsConfigurator;
import com.wyc.annotation.IdAnnotation2;
import com.wyc.annotation.ParamAnnotation2;
import com.wyc.annotation.ParamEntityAnnotation2;
class Param{
	private String type;
	private String name;
	private Object value;
	private String id;
	private String entityName;
	private Field field;
	private boolean isId;
	private boolean isMap;

	public boolean isMap() {
		return isMap;
	}
	public void setIsMap(boolean isMap) {
		this.isMap = isMap;
	}
	public boolean isId() {
		return isId;
	}
	public void setIsId(boolean isId) {
		this.isId = isId;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}

public class SessionManager<T> {
	
	
	private Object returnValue;
	
	private boolean isEnd;
	private HttpServletRequest httpServletRequest;
	private HttpSession httpSession;
	private ServletContext servletContext;
	
	private T params;
	
	private Map<String,Object> returnAttribute;
	
	
	
	public static final String contextId = UUID.randomUUID().toString();
	private static final ThreadLocal<SessionManager> filterManagerThreadLocal = new ThreadLocal<>();
	
	final static Logger logger = LoggerFactory.getLogger(SessionManager.class);
	
	


	public Map<String, Object> getReturnAttribute() {
		return returnAttribute;
	}


	public void setReturnAttribute(Map<String, Object> returnAttribute) {
		this.returnAttribute = returnAttribute;
	}


	public Object getReturnValue() {
		return returnValue;
	}


	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}


	public boolean isEnd() {
		return isEnd;
	}


	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}


	private SessionManager(HttpServletRequest httpServletRequest,Class<T> paramClass)throws Exception{
		this.httpServletRequest = httpServletRequest;
		this.httpSession = this.httpServletRequest.getSession();
		this.servletContext = this.httpServletRequest.getServletContext();
		
		if(paramClass.isAssignableFrom(Map.class)){
			Map<String, Object> obj = toParamObjectOfMap(httpServletRequest);
			params = (T)obj;
		}else{
			params = toParamObject(paramClass, httpServletRequest);
			save(params);
		}
	}
	
	
	public static synchronized SessionManager getFilterManager(HttpServletRequest httpServletRequest,Class<?> paramClass)throws Exception{
		/*SessionManager<?> filterManager = filterManagerThreadLocal.get();
		if(filterManager==null){
			filterManager = new SessionManager<>(httpServletRequest, paramClass);
			filterManagerThreadLocal.set(filterManager);
		}
		return filterManager;*/
		
		String sessionManagerId = "sessionManager_"+httpServletRequest.getRequestedSessionId();
		if(httpServletRequest.getAttribute(sessionManagerId)!=null){
			return (SessionManager)httpServletRequest.getAttribute(sessionManagerId);
		}else{
			SessionManager filterManager = new SessionManager<>(httpServletRequest, paramClass);
			httpServletRequest.setAttribute(sessionManagerId, filterManager);
			return filterManager;
		}
		
	}
	
	public static synchronized SessionManager getFilterManager(HttpServletRequest httpServletRequest)throws Exception{
	//	return filterManagerThreadLocal.get();
		String sessionManagerId = "sessionManager_"+httpServletRequest.getRequestedSessionId();
		return (SessionManager)httpServletRequest.getAttribute(sessionManagerId);
		
	}
	
	
	
	public T getParams() {
		return params;
	}


	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}


	public void rawSaveToRequest(String key,Object value){
		
		httpServletRequest.setAttribute(key, value);
	}
	
	public void rawSaveToSession(String key , Object value){
		httpSession.setAttribute(key, value);
	}
	
	public void rawSaveToContext(String key , Object value){
	
		servletContext.setAttribute(key, value);
	}
	
	public void rawRemoveKeyByRequest(String key){
		httpServletRequest.removeAttribute(key);
	}
	
	public void rawRemoveKeyBySession(String key){
		httpServletRequest.getSession().removeAttribute(key);
	}
	
	public void rawRemoveKeyByContext(String key){
		httpServletRequest.getSession().getServletContext().removeAttribute(key);
	}
	
	public Object rawGetByRequest(String key){
		return httpServletRequest.getAttribute(key);
	}
	
	
	
	public Object rawGetBySession(String key){
		return httpSession.getAttribute(key);
	}
	
	public Object rawGetByContext(String key){
		return servletContext.getAttribute(key);
	}
	
	
	private Map<String, Object> toParamObjectOfMap(HttpServletRequest httpServletRequest)throws Exception{
		Enumeration<String> enumeration = httpServletRequest.getParameterNames();
		 Map<String, Object> map = new HashMap<>();
		 while(enumeration.hasMoreElements()){
			 String name = enumeration.nextElement();
			 map.put(name,httpServletRequest.getParameter(name));
		 }
		 return map;
	}
	
	private T toParamObject(Class<T> clazz ,HttpServletRequest httpServletRequest)throws Exception{
		
		
			T target = clazz.newInstance();
			
			Field[] fileds = clazz.getDeclaredFields();
			for(Field field:fileds){
				field.setAccessible(true);
				ParamAnnotation2 paramAnnotation2 = field.getAnnotation(ParamAnnotation2.class);
				if(paramAnnotation2!=null){
					String name = null;
					if(paramAnnotation2.name().equals("")){
						name = field.getName();
					}else{
						name = paramAnnotation2.name();
					}
					String value = httpServletRequest.getParameter(name);
					field.set(target, value);
				}
			}
			
			save(target);
			return target;
	}
		
	
	
	
	public Map<String, Object> getAllContextAttributes(){
		 Enumeration<String> enumeration = httpServletRequest.getSession().getServletContext().getAttributeNames();
		 Map<String, Object> map = new HashMap<>();
		 while(enumeration.hasMoreElements()){
			 String name = enumeration.nextElement();
			 if(!name.startsWith("org.springframework")&&
					 !name.startsWith("com.sun")&&
					 !name.startsWith("sessionManager")&&
					 !name.startsWith("CharacterEncodingFilter")&&
					 !name.startsWith("org.eclipse")&&
					 !name.startsWith("org.apache")&&
					 !name.startsWith("javax.servlet")){
				 map.put(name,httpServletRequest.getSession().getServletContext().getAttribute(name));
			 }
				
		 }
		 return map;
	}
	
	
	public Map<String, Object> getAllSessionAttributes(){
		 Enumeration<String> enumeration = httpServletRequest.getSession().getAttributeNames();
		 Map<String, Object> map = new HashMap<>();
		 while(enumeration.hasMoreElements()){
			 String name = enumeration.nextElement();
			 if(!name.startsWith("org.springframework")&&
					 !name.startsWith("com.sun")&&
					 !name.startsWith("sessionManager")&&
					 !name.startsWith("CharacterEncodingFilter")&&
					 !name.startsWith("org.eclipse")&&
					 !name.startsWith("org.apache")&&
					 !name.startsWith("javax.servlet")){
				 	map.put(name,httpServletRequest.getSession().getAttribute(name));
			 }
		 }
		 return map;
	}
	
	
	public Map<String, Object> getAllRequestAttributes(){
		 Enumeration<String> enumeration = httpServletRequest.getAttributeNames();
		 Map<String, Object> map = new HashMap<>();
		 while(enumeration.hasMoreElements()){
			 String name = enumeration.nextElement();
			 if(!name.startsWith("org.springframework")&&
					 !name.startsWith("com.sun")&&
					 !name.startsWith("sessionManager")&&
					 !name.startsWith("CharacterEncodingFilter")&&
					 !name.startsWith("org.eclipse")&&
					 !name.startsWith("org.apache")&&
					 !name.startsWith("javax.servlet")){
				 		map.put(name,httpServletRequest.getAttribute(name));
			 }
		 }
		 return map;
	}
	
	public Object getObject(Class<?> type)throws Exception{
		
		Object id = null;
		
		ParamEntityAnnotation2 paramEntityAnnotation2 = type.getAnnotation(ParamEntityAnnotation2.class);
		if(paramEntityAnnotation2.type().equals(ParamAnnotation2.REQUEST_TYPE)){
			id = rawGetByRequest(idRef(type));
		}else if(paramEntityAnnotation2.type().equals(ParamAnnotation2.SESSION_TYPE)){
			id = rawGetBySession(idRef(type));
		}else if(paramEntityAnnotation2.type().equals(ParamAnnotation2.CONTEXT_TYPE)){
			id = rawGetByContext(idRef(type));
		}
		

		
		if(id!=null){
			Object obj = getObject(type, id+"");

			return obj;
		}
		
		return null;
		
		
	}

	public Object getObject(Class<?> type , String id)throws Exception{
		
		Object obj = type.newInstance();
		Field[] fields = type.getDeclaredFields();
		for(Field field:fields){
			field.setAccessible(true);
			
			if(field.getAnnotation(IdAnnotation2.class)!=null){
				field.set(obj, id);
			}
			
			if(field.getAnnotation(ParamAnnotation2.class)!=null){
				String name = name(field);
				Object value = getValue(type, id, name);
				field.set(obj, value);
			}
		}
		return obj;
		
	}
	
	
	public void remove(Class<?>type)throws Exception{
		Object id = null;
		
		ParamEntityAnnotation2 paramEntityAnnotation2 = type.getAnnotation(ParamEntityAnnotation2.class);
		if(paramEntityAnnotation2.type().equals(ParamAnnotation2.REQUEST_TYPE)){
			id = rawGetByRequest(idRef(type));
		}else if(paramEntityAnnotation2.type().equals(ParamAnnotation2.SESSION_TYPE)){
			id = rawGetBySession(idRef(type));
		}else if(paramEntityAnnotation2.type().equals(ParamAnnotation2.CONTEXT_TYPE)){
			id = rawGetByContext(idRef(type));
		}
		
		remove(type,id+"");
		
		rawRemoveKeyByContext(idRef(type));
		rawRemoveKeyByRequest(idRef(type));
		rawRemoveKeyBySession(idRef(type));
	}
	
	public void remove(Class<?> type , String id)throws Exception{
		Field[] fields = type.getDeclaredFields();
		
		for(Field field:fields){
			if(field.getAnnotation(IdAnnotation2.class)!=null||field.getAnnotation(ParamAnnotation2.class)!=null){
				String name = name(field);
				String key = key(type, id, name);
				removeValueByKey(key);
			}
		}
	}
	
	
	public void removeValueByKey(String key){
		rawRemoveKeyByContext(key);
		rawRemoveKeyByRequest(key);
		rawRemoveKeyBySession(key);
	}
	
	public Object getValueByKey(String key){
		Object value = null;
		value = rawGetByRequest(key);

		if(value==null){
			value = rawGetBySession(key);

		}
		if(value==null){
			value = rawGetByContext(key);
		}
		return value;
	}
	
	public Object getValue(Class<?> type , String id , String name)throws Exception{
		String key = key(type, id, name);
		
		return getValueByKey(key);
	}
	
	
	
	private String getSessionId(Class<?> clazz){
		ParamEntityAnnotation2 paramEntityAnnotation2 = clazz.getAnnotation(ParamEntityAnnotation2.class);
		String type = paramEntityAnnotation2.type();
		if(type.equals(ParamAnnotation2.REQUEST_TYPE)){
			return httpServletRequest.getRequestedSessionId();
		}else if(type.equals(ParamAnnotation2.SESSION_TYPE)){
			return httpServletRequest.getSession().getId();
		}else if(type.equals(ParamAnnotation2.CONTEXT_TYPE)){
			return contextId;
		}
		return null;
	}
	
	public void save(Object obj)throws Exception{
		
		Class<?> clazz = obj.getClass();
	//	remove(clazz);
		Object idValue = null;
		List<Param> params = getParams(obj);
		for(Param param:params){
			String type = param.getType();
			if(param.isId()){
				idValue = param.getValue();
			}
			

			if(type.equals(ParamAnnotation2.REQUEST_TYPE)){

				rawSaveToRequest(param.getName(), param.getValue());
			}else if(type.equals(ParamAnnotation2.SESSION_TYPE)){

				rawSaveToSession(param.getName(), param.getValue());
			}else if(type.equals(ParamAnnotation2.CONTEXT_TYPE)){

				rawSaveToContext(param.getName(), param.getValue());
			}
		}
		
		ParamEntityAnnotation2 paramEntityAnnotation2 = clazz.getAnnotation(ParamEntityAnnotation2.class);
		
		if(paramEntityAnnotation2.type().equals(ParamEntityAnnotation2.REQUEST_TYPE)){
			rawSaveToRequest(idRef(clazz), idValue);
		}else if(paramEntityAnnotation2.type().equals(ParamEntityAnnotation2.SESSION_TYPE)){
			rawSaveToSession(idRef(clazz), idValue);
		}else if(paramEntityAnnotation2.type().equals(ParamEntityAnnotation2.CONTEXT_TYPE)){
			rawSaveToContext(idRef(clazz), idValue);
		}
	}
	
	
	private String idRef(Class<?> type)throws Exception{
		String className = type.getName();
		String sessionId = getSessionId(type);
		return className + "_" + sessionId;
	}
	
	private String entityName(Class<?> type)throws Exception{
		String entityName = null;
		ParamEntityAnnotation2 paramEntityAnnotation2 = type.getAnnotation(ParamEntityAnnotation2.class);
		if(paramEntityAnnotation2.name().equals("")){
			entityName = type.getName();
		}else{
			entityName = paramEntityAnnotation2.name();
		}
		return entityName;
	}
	
	private String name(Field field){
		field.setAccessible(true);
		ParamAnnotation2 paramAnnotation2 = field.getAnnotation(ParamAnnotation2.class);
		IdAnnotation2 idAnnotation2 = field.getAnnotation(IdAnnotation2.class);
		String name = null;
		
		if(idAnnotation2!=null){
			if(idAnnotation2.name().equals("")){
				name = field.getName();
			}else{
				name = paramAnnotation2.name();
			}
			return name;
		}
		if(paramAnnotation2.name().equals("")){
			name = field.getName();
		}else{
			name = paramAnnotation2.name();
		}
		return name;
	}
	
	
	private String key(String entry,String id , String name)throws Exception{
		String key = entry+"_"+id+"_"+name;
		return key;
	}
	
	private String key(Class<?> type , String id , String name)throws Exception{
		
		String entryName = entityName(type);
		Field field = type.getDeclaredField(name);
		name = name(field);
		return key(entryName, id, name);
		
	}
	
	private List<Param> getParams(String entryName,Map<String, Object> map)throws Exception{
		List<Param> params = new ArrayList<>();
		
		String idValue = UUID.randomUUID().toString();
		String idName = "id";
		String idKey = key(entryName, idValue, idName);
		Param idParam = new Param();
		idParam.setEntityName(entryName);

		idParam.setId(idValue);
		idParam.setIsId(true);
		idParam.setIsMap(false);
		idParam.setName(idKey);
		idParam.setType(ParamEntityAnnotation2.REQUEST_TYPE);
		idParam.setValue(idValue);
		
		for(Entry<String, Object> entry:map.entrySet()){
			Param param = new Param();
			
			String paramKey = key(entryName, idValue, entry.getKey());
			param.setEntityName(entryName);
			
			param.setId(idValue);
			param.setIsId(false);
			param.setIsMap(false);
			param.setName(paramKey);
			param.setType(ParamEntityAnnotation2.REQUEST_TYPE);
			param.setValue(entry.getValue());
			params.add(param);
		}
		
		return params;
		
	}
	
	private List<Param> getParams(Object obj)throws Exception{
		Class<?> type = obj.getClass();
		Field[] fields = type.getDeclaredFields();
		
		List<Param> params = new ArrayList<>();
		Field idField = null;
		List<Field> paramFields = new ArrayList<>();
		for(Field field:fields){
			IdAnnotation2 idAnnotation2 = field.getAnnotation(IdAnnotation2.class);
			if(idAnnotation2!=null){
				idField = field;
			}
			
			ParamAnnotation2 paramAnnotation2 = field.getAnnotation(ParamAnnotation2.class);
			if(paramAnnotation2!=null){
				paramFields.add(field);
			}
		}
		
		String idName = null;
		String idValue = null;
		if(idField!=null){
			idName = name(idField);
			if(idField.get(obj)!=null){
				idValue = idField.get(obj)+"";
			}
			
		}else{
			idName = "id";
			idValue = UUID.randomUUID().toString();
		}
		
		if(idValue==null){
			idValue = UUID.randomUUID().toString();
		}
		String idKey = key(type, idValue, idName);
		ParamEntityAnnotation2 paramEntityAnnotation2 = type.getAnnotation(ParamEntityAnnotation2.class);
		
		Param idParam = new Param();
		String entityName = entityName(type);
		idParam.setEntityName(entityName);
		idParam.setField(idField);
		idParam.setId(idValue);
		idParam.setIsId(true);
		idParam.setIsMap(false);
		idParam.setName(idKey);
		idParam.setType(paramEntityAnnotation2.type());
		idParam.setValue(idValue);
		
		params.add(idParam);
		for(Field paramField:paramFields){
			
			paramField.setAccessible(true);
			ParamAnnotation2 paramAnnotation2 = paramField.getAnnotation(ParamAnnotation2.class);
			
			String paramName = name(paramField);
			String paramType = paramAnnotation2.type();
			
			String paramKey = key(type, idValue, paramName);
			Object paramValue = paramField.get(obj);

			Param param = new Param();
			param.setEntityName(entityName);
			param.setField(paramField);
			param.setId(idValue);
			param.setIsId(false);
			param.setIsMap(false);
			param.setName(paramKey);
			param.setType(paramType);
			param.setValue(paramValue);
			params.add(param);
		}
		
		return params;
	}
}
