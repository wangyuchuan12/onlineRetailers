package com.wyc.wx.controller.action.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.service.TokenService;
import com.wyc.wx.domain.AccessTokenBean;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.domain.JsapiTicketBean;
import com.wyc.wx.domain.Result;
import com.wyc.wx.domain.Token;
import com.wyc.wx.domain.UserInfo;
import com.wyc.wx.service.BasicSupportService;
import com.wyc.wx.service.JsapiTicketService;
import com.wyc.wx.service.MenuService;
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
    @Autowired
    private MenuService menuService;
    @Autowired
    private TokenService tokenservice;
    @Autowired
    private JsapiTicketService jsapiTickService;
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
    public String getWebpageAuthorizeUrl(HttpServletRequest httpRequest)throws Exception{
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
    public static void main(String[]args){
        System.out.println(new ServiceTest().createMenu());
    }
    @RequestMapping("/wx/create_menu_test")
    public Result createMenu(){
        try {
            String responeJsonStr = "{"+
                    "\"button\":["+
                        "{\"name\":\"进入商城\","+
                        "\"type\":\"view\"," +
                        "\"url\":\"http://www.chengxihome.com/main/good_list\"," +
                        "\"key\":\"V01_S01\"" +
                        "},"+
                        "{\"name\":\"商城须知\","+
                        "\"type\":\"click\"," +
                        "\"key\":\"V02_S01\"" +
                        "}"+
                    "]"+
                "}";
            AccessTokenBean accessToken = basicSupportService.getAccessTokenBean();
            return menuService.createMenu(responeJsonStr, accessToken.getAccessToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping("/service/get_token")
    public Token getToken(){
        return tokenservice.findByIdAndInvalidDateGreaterThan("2",new DateTime());
    }
    
    @RequestMapping("/data/json_store_data")
    public Object jsonStoreTest(HttpServletRequest httpServletRequest){
        Map<String, Object> map = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> item = new HashMap<String, String>();
        item.put("id", 123+"");
        item.put("name", "wyc2");
        list.add(item);
        map.put("root", list);
        return map;
    }
    
    @RequestMapping("/data/jsapi_ticket")
    public Object JsapiTicketTest(HttpServletRequest httpServletRequest)throws Exception{
        AccessTokenBean accessTokenBean = basicSupportService.getAccessTokenBean();
        
        return jsapiTickService.getJsapiTicketBean(accessTokenBean.getAccessToken());
    }
}
