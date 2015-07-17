package com.wyc.intercept;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.wyc.intercept.domain.AfterBean;
import com.wyc.intercept.domain.BeforeBean;
import com.wyc.intercept.domain.ResponseBean;
public abstract class BaseIntercept {
    protected HttpServletRequest httpServletRequest;
    protected Map<String, MultipartFile> multipartFiles = new HashMap<String, MultipartFile>();
    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }
    
    public void setMultipartFile(String name , MultipartFile multipartFile){
        multipartFiles.put(name, multipartFile);
    }
    
    public void getMultipartFile(String name){
        multipartFiles.get(name);
    }
    public abstract BeforeBean before();
    public abstract AfterBean after(ResponseBean responseBean);
    public abstract ResponseBean response(BeforeBean beforeBean);
    public abstract String exception(Exception e);
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
    
    public void setAttribute(String name , Object target){
        httpServletRequest.setAttribute(name, target);
    }
    
    public String getParameter(String name){
        return httpServletRequest.getParameter(name);
    }
}
