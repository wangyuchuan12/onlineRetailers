package com.wyc.intercept.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.service.TokenService;
import com.wyc.service.WxUserInfoService;
import com.wyc.wx.domain.AccessTokenBean;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.domain.Token;
import com.wyc.wx.domain.UserInfo;
import com.wyc.wx.service.UserService;
@Service
public class UserInterceptService implements InterceptService<UserInfo>{
    private String code;
    @Autowired
    private UserService userService;
    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Autowired
    private AuthorizeInterceptService authorizeInterceptService;
    @Autowired
    private AccessTokenInterceptService accessTokenService;
    @Autowired
    private TokenService tokenService;
    public void setCode(String code){
        this.code = code;
    }
    @Override
    public UserInfo getFromWx() throws Exception{
        AccessTokenBean accessTokenBean = accessTokenService.getFromWx();
        authorizeInterceptService.setCode(code);
        Authorize authorize = authorizeInterceptService.getFromWx();
        UserInfo userInfo = userService.getUserInfoFromWeb(accessTokenBean.getAccess_token(), authorize.getOpenid(), 1);
        return userInfo;
    }

    @Override
    public boolean localValid(String tokenId) {
        Token token = tokenService.findByIdAndInvalidDateGreaterThan(tokenId, new DateTime());
        if(token==null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public UserInfo getFromDatabase(String token) {
        // TODO Auto-generated method stub
        return wxUserInfoService.findByToken(token);
    }

    @Override
    public Token saveToDatabase(UserInfo t) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 24);
        Token token = new Token();
        token.setStatus(1);
        token.setInvalidDate(new DateTime(calendar.getTime()));
        token = tokenService.add(token);
        t.setToken(token.getId());
        wxUserInfoService.add(t);
        return token;
    }

    
}
