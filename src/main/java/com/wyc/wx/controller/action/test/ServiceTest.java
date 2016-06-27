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

import com.fasterxml.jackson.databind.ObjectMapper;
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
        	Map<String, Object> map = new HashMap<>();
        	List<Object> buttons = new ArrayList<>();
        	Map<String, Object> button1 = new HashMap<>();
        	button1.put("name", "进入商场");
        	button1.put("type", "view");
        	button1.put("url", "http://www.chengxihome.com/main/good_list");
        	button1.put("key", "V01_S01");
        	
        	Map<String, Object> button3 = new HashMap<>();
        	button3.put("name", "抽奖");
                button3.put("type", "view");
                button3.put("url", "http://www.chengxihome.com/game/luck_draw");
                button3.put("key", "V01_S01");
        	
        	Map<String, Object> button2 = new HashMap<>();
        	button2.put("name", "类别");
//        	button2.put("type", "view");
//        	button2.put("url", "http://www.chengxihome.com/main/good_list");
//        	button2.put("key", "V02_S01");
        	
        	List<Map<String, Object>> typeSubButtons = new ArrayList<Map<String,Object>>();
        	Map<String, Object> typeSubButton1 = new HashMap<String, Object>();
        	Map<String, Object> typeSubButton2 = new HashMap<String, Object>();
        	Map<String, Object> typeSubButton3 = new HashMap<String, Object>();
        	typeSubButton1.put("name", "袜子");
        	typeSubButton1.put("type", "view");
        	typeSubButton1.put("url", "http://www.chengxihome.com/main/good_list?good_type=7999ea9e-f8ba-4225-ba1f-a4625bbaabd0");
        	typeSubButton1.put("key", "V02_S01");
        	
        	typeSubButton2.put("name", "日用百货");
                typeSubButton2.put("type", "view");
                typeSubButton2.put("url", "http://www.chengxihome.com/main/good_list?good_type=7be223ce-dd4e-4e73-ba9f-8be0a4799cb3");
                typeSubButton2.put("key", "V02_S01");
                
                typeSubButton3.put("name", "首饰");
                typeSubButton3.put("type", "view");
                typeSubButton3.put("url", "http://www.chengxihome.com/main/good_list?good_type=6bbc5d5f-2fb9-4a8b-8ec5-156155a95b71");
                typeSubButton3.put("key", "V02_S01");
        	
        	typeSubButtons.add(typeSubButton1);
        	typeSubButtons.add(typeSubButton2);
        	typeSubButtons.add(typeSubButton3);
        	button2.put("sub_button", typeSubButtons);
                
                
        	buttons.add(button1);
        	buttons.add(button2);
        	buttons.add(button3);
        	map.put("button", buttons);
        	ObjectMapper objectMapper = new ObjectMapper();
        	String responseJsonStr = objectMapper.writeValueAsString(map);
            /*String responeJsonStr = "{"+
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
             */
        	System.out.println(responseJsonStr);
            AccessTokenBean accessToken = basicSupportService.getAccessTokenBean();
            return menuService.createMenu(responseJsonStr, accessToken.getAccessToken());
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
