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
import com.wyc.service.WxAccessTokenService;
import com.wyc.wx.domain.AccessTokenBean;
import com.wyc.wx.domain.Token;
import com.wyc.wx.domain.WxContext;
import com.wyc.wx.service.BasicSupportService;
@Service
public class AccessTokenSmartService implements SmartService<AccessTokenBean>{
    @Autowired
    private BasicSupportService basicSuppertService;
    @Autowired
    private WxAccessTokenService wxAccessTokenService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private WxContext wxContext;
    
    private String accessToken;
    final static Logger logger = LoggerFactory.getLogger(AccessTokenSmartService.class);
    
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public AccessTokenBean getFromWx()throws Exception{
        AccessTokenBean accessTokenBean = basicSuppertService.getAccessTokenBean();
        logger.debug("get accessTokenBean from wx,the object is {}",accessTokenBean);
        return accessTokenBean;
    }
    
    @Override
    public AccessTokenBean getFromDatabase(String token) {
        AccessTokenBean accessTokenBean = wxAccessTokenService.findByToken(token);
        logger.debug("get AccessTokenBean from database,the object is {}",accessTokenBean);
        return accessTokenBean;
    }

    @Override
    public Token saveToDatabase(AccessTokenBean t,String tokenKey) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, Integer.parseInt(t.getExpiresIn())-100);
        tokenService.deleteByTokenKey(tokenKey);
        Token token = new Token();
        token.setStatus(1);
        token.setInvalidDate(new DateTime(calendar.getTime()));
        token.setTokenKey(tokenKey);
        token = tokenService.add(token);
        t.setToken(token.getId());
        wxAccessTokenService.add(t);
        logger.debug("save the accessTokenBean to database,the accessTokenbean is {},the token is {}",t,token.getId());
        return token;
    }

    @Override
    public AccessTokenBean getFromDatabaseByOther() {
        return wxAccessTokenService.findByAccessToken(accessToken);
    }

    @Override
    public String generateKey(String... args) {
        String appid = wxContext.getAppid();
        String appscret = wxContext.getAppsecret();
        StringBuffer sb = new StringBuffer();
        sb.append("accessToken_");
        sb.append(appid);
        sb.append(appscret);
        sb.append("_");
        for(String str:args){
            sb.append(str);
            sb.append("-");
        }
        return sb.toString();
    }



    @Override
    public AccessTokenBean getFromDatabaseByKey(String key) {
        Token token = tokenService.findByKeyAndInvalidDateGreaterThan(key, new DateTime());
        if(token!=null){
            AccessTokenBean accessTokenBean = wxAccessTokenService.findByToken(token.getId());
            return accessTokenBean;
        }
        return null;
    }
}
