package com.wyc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//制定某个对象所对应的类名称
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamEntityAnnotation2 {
	
	
	
	public static final String REQUEST_TYPE="request";
	public static final String SESSION_TYPE="session";
	public static final String CONTEXT_TYPE="context";
	
	
	public String type()default REQUEST_TYPE;
	
	public String name()default "";
}
