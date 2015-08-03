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
import com.wyc.service.WxAuthorizeService;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.domain.Token;
import com.wyc.wx.service.OauthService;
@Service
public class AuthorizeInterceptService implements InterceptService<Authorize>{
    private String code;
    @Autowired
    private OauthService oauthService;
    @Autowired
    private WxAuthorizeService wxAuthorizeService;
    @Autowired
    private TokenService tokenService;
    final static Logger logger = LoggerFactory.getLogger(AuthorizeInterceptService.class);
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Authorize getFromWx() throws Exception {
        Authorize authorize = oauthService.getAuthorizeByCode(code);
        logger.debug("get authorize from wx,the object is {}",authorize);
        return authorize;
    }

    @Override
    public boolean localValid(String tokenId) throws Exception {
        Token token = tokenService.findByIdAndInvalidDateGreaterThan(tokenId, new DateTime());
        if(token==null)
        {
            logger.debug("check local token is null");
            return false;
        }else{
            logger.debug("check local token is not null");
            return true;
        }
    }

    @Override
    public Authorize getFromDatabase(String token) {
        Authorize authorize = wxAuthorizeService.findByToken(token);
        logger.debug("get authorize from database,the object is {}",authorize);
        return authorize;
    }

    @Override
    public Token saveToDatabase(Authorize t) throws Exception {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 24);
        Token token = new Token();
        token.setStatus(1);
        token.setInvalidDate(new DateTime(calendar.getTime()));
        token = tokenService.add(token);
        t.setToken(token.getId());
        wxAuthorizeService.add(t);
        logger.debug("save the Authorize to database,the Authorize is {},the token is {}",t,token.getId());
        return token;
    }

}
