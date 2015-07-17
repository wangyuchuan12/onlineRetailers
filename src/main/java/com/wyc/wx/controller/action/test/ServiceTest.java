package com.wyc.wx.controller.action.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.wx.domain.AccessTokenBean;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.domain.UserInfo;
import com.wyc.wx.service.BasicSupportService;
import com.wyc.wx.service.OauthService;
import com.wyc.wx.service.UserService;

@RestController
public class ServiceTest {
    @Autowired
    private BasicSupportService basicSupportService;
    @Autowired
    private OauthService oauthService;
    @Autowired
    private UserService userService;
    @RequestMapping("/wx/getAccessTokenBean_test")
    public AccessTokenBean getAccessTokenBeanTest(HttpServletRequest httpRequest){
        try {
            AccessTokenBean accessTokenBean = basicSupportService.getAccessTokenBean();
            return accessTokenBean;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("/wx/getWebpageAuthorizeUrl_test")
    public String getWebpageAuthorizeUrl(HttpServletRequest httpRequest){
        String redirectUri = httpRequest.getParameter("redirectUri");
        String scope = httpRequest.getParameter("scope");
        String state = httpRequest.getParameter("state");
        return oauthService.getWebpageAuthorizeUrl(redirectUri,Integer.parseInt(scope), state);
    }
    
    @RequestMapping("/wx/getAuthorizeByCode_test")
    public Authorize getAuthorizeByCode(HttpServletRequest httpRequest){
        try {
            return oauthService.getAuthorizeByCode(httpRequest.getParameter("code"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping("/wx/getUserInfo_test")
    public UserInfo getUserInfo(HttpServletRequest httpRequest){
        try {
            String accessToken = httpRequest.getParameter("access_token");
            String openid = httpRequest.getParameter("openid");
            String lang_type = httpRequest.getParameter("lang_type");
            return userService.getUserInfo(accessToken, openid, Integer.parseInt(lang_type));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping("/wx/getSnUserInfo_test")
    public UserInfo getSnUserInfo(HttpServletRequest httpRequest){
        try {
            String accessToken = httpRequest.getParameter("access_token");
            String openid =  httpRequest.getParameter("openid");
            int lang_type = Integer.parseInt(httpRequest.getParameter("lang_type"));
            return userService.getUserInfoFromWeb(accessToken, openid, lang_type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
