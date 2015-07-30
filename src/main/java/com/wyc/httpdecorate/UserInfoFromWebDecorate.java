package com.wyc.httpdecorate;
import org.springframework.beans.factory.annotation.Autowired;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.domain.UserInfo;
import com.wyc.wx.service.UserService;

public class UserInfoFromWebDecorate implements BaseHttpDecorate{
    private MyHttpServletRequest myHttpServletRequest;
    private Authorize authorize;
    @Autowired
    private UserService userService;
    public UserInfoFromWebDecorate(MyHttpServletRequest myHttpServletRequest,Authorize authorize){
        this.myHttpServletRequest = myHttpServletRequest;
        this.authorize = authorize;
    }
    
    public MyHttpServletRequest execute()throws Exception{
        System.out.println("userService......."+userService);
        System.out.println("authorize......."+authorize);
        System.out.println("getAccess_token......."+authorize.getAccess_token());
        UserInfo userInfo = userService.getUserInfoFromWeb(authorize.getAccess_token(), authorize.getOpenid(), 0);
        myHttpServletRequest.setUserInfo(userInfo);
        return myHttpServletRequest;
    }
}
