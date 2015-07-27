package com.wyc.httpdecorate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.wx.domain.AccessTokenBean;
import com.wyc.wx.service.BasicSupportService;

public class AccessTokenDecorate implements BaseHttpDecorate{
    private MyHttpServletRequest myHttpServletRequest;
    @Autowired
    private BasicSupportService basicSupportService;
    public AccessTokenDecorate(HttpServletRequest httpServletRequest) {
        myHttpServletRequest = new MyHttpServletRequest(httpServletRequest);
    }
    
    public AccessTokenDecorate(MyHttpServletRequest myHttpServletRequest){
        this.myHttpServletRequest = myHttpServletRequest;
    }
    
    public MyHttpServletRequest execute()throws Exception{
        AccessTokenBean accessTokenBean = basicSupportService.getAccessTokenBean();
        myHttpServletRequest.setAccessTokenBean(accessTokenBean);
        return myHttpServletRequest;
    }
}
