package com.wyc.smart.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.service.TokenService;
import com.wyc.service.WxUserInfoService;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.domain.Token;
import com.wyc.wx.domain.UserInfo;
import com.wyc.wx.service.UserService;
@Service
public class UserSmartService implements SmartService<UserInfo>{
    private String code;
    private String openid;
    @Autowired
    private UserService userService;
    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Autowired
    private AuthorizeSmartService authorizeInterceptService;
    @Autowired
    private AccessTokenSmartService accessTokenService;
    @Autowired
    private TokenService tokenService;
    
    final static Logger logger = LoggerFactory.getLogger(UserSmartService.class);
    public void setCode(String code){
        this.code = code;
    }
    
    public void setOpenid(String openid){
        this.openid = openid;
    }
    
    @Override
    public UserInfo getFromWx() throws Exception{
        authorizeInterceptService.setCode(code);
        Authorize authorize = authorizeInterceptService.getFromWx();
        UserInfo userInfo = userService.getUserInfoFromWeb(authorize.getAccess_token(), authorize.getOpenid(), 1);
        logger.debug("get UserInfo from wx,the object is {}",userInfo);
        return userInfo;
    }

    @Override
    public boolean localValid(String tokenId) {
        Token token = tokenService.findByIdAndInvalidDateGreaterThan(tokenId, new DateTime());
        if(token==null){
            logger.debug("check local token is null");
            return false;
        }else{
            logger.debug("check local token is not null");
            return true;
        }
    }

    @Override
    public UserInfo getFromDatabase(String token) {
        UserInfo userInfo = null;
        if(token!=null){
            userInfo = wxUserInfoService.findByToken(token);
            logger.debug("get UserInfo from database by token,the object is {},the token is {}",userInfo,token);
        }
        return userInfo;
    }
    
    @Override
    public UserInfo getFromDatabaseByOther(){
        return wxUserInfoService.findByOpenid(openid);
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
        logger.debug("save the userInfo to database,the UserInfo is {},the token is {}",t,token.getId());
        return token;
    }

    @Override
    public String generateKey(String ... args) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean duplicate(String key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public UserInfo getFromDatabaseByKey(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    
}
