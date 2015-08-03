package com.wyc.intercept.service;

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
import com.wyc.wx.service.BasicSupportService;
@Service
public class AccessTokenInterceptService implements InterceptService<AccessTokenBean>{
    @Autowired
    private BasicSupportService basicSuppertService;
    @Autowired
    private WxAccessTokenService wxAccessTokenService;
    @Autowired
    private TokenService tokenService;
    final static Logger logger = LoggerFactory.getLogger(AccessTokenInterceptService.class);
    @Override
    public AccessTokenBean getFromWx()throws Exception{
        AccessTokenBean accessTokenBean = basicSuppertService.getAccessTokenBean();
        logger.debug("get accessTokenBean from wx,the object is {}",accessTokenBean);
        return accessTokenBean;
    }

    @Override
    public boolean localValid(String tokenId) {
        Token token = tokenService.findByIdAndInvalidDateGreaterThan(tokenId, new DateTime());
        if(token!=null){
            logger.debug("check local token is null");
            return true;
        }else{
            logger.debug("check local token is not null");
            return false;
        }
    }

    @Override
    public AccessTokenBean getFromDatabase(String token) {
        AccessTokenBean accessTokenBean = wxAccessTokenService.findByToken(token);
        logger.debug("get AccessTokenBean from database,the object is {}",accessTokenBean);
        return accessTokenBean;
    }

    @Override
    public Token saveToDatabase(AccessTokenBean t) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 24);
        Token token = new Token();
        token.setStatus(1);
        token.setInvalidDate(new DateTime(calendar.getTime()));
        token = tokenService.save(token);
        t.setToken(token.getId());
        wxAccessTokenService.add(t);
        logger.debug("save the accessTokenBean to database,the accessTokenbean is {},the token is {}",t,token.getId());
        return token;
    }
}
