package com.wyc.manage.domain.vo;

import com.wyc.annotation.IdAnnotation2;
import com.wyc.annotation.ParamAnnotation2;
import com.wyc.annotation.ParamEntityAnnotation2;

@ParamEntityAnnotation2
public class WxConfigBean {
	
	@IdAnnotation2
	private String id;
	@ParamAnnotation2
	private String signature;
	
	@ParamAnnotation2
    private String noncestr;
	
	@ParamAnnotation2
    private String appId;
	
	@ParamAnnotation2
    private String datetime;

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	
	
}
