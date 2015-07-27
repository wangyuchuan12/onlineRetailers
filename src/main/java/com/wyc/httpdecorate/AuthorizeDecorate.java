package com.wyc.httpdecorate;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.service.OauthService;
import com.wyc.wx.service.UserService;

public class AuthorizeDecorate implements BaseHttpDecorate{
    private String code;
    @Autowired
    private OauthService oauthService;
    @Autowired
    private UserService userService;
    private MyHttpServletRequest myHttpServletRequest;
    public AuthorizeDecorate(HttpServletRequest httpServletRequest,String code){
        this.code = code;
        this.myHttpServletRequest = new MyHttpServletRequest(httpServletRequest);
    }
    public AuthorizeDecorate(MyHttpServletRequest myHttpServletRequest, String code){
        this.code = code;
        this.myHttpServletRequest = myHttpServletRequest;
    }
    
    public MyHttpServletRequest execute()throws Exception{
        Authorize authorize = oauthService.getAuthorizeByCode(code);
        this.myHttpServletRequest.setAuthorize(authorize);
        return myHttpServletRequest;
    }
    
}
