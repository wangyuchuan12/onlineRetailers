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
import com.wyc.service.WxAuthorizeService;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.domain.Token;
import com.wyc.wx.service.OauthService;
@Service
public class AuthorizeSmartService implements SmartService<Authorize>{
    private String code;
    private String openid;
    @Autowired
    private OauthService oauthService;
    @Autowired
    private WxAuthorizeService wxAuthorizeService;
    @Autowired
    private TokenService tokenService;
    final static Logger logger = LoggerFactory.getLogger(AuthorizeSmartService.class);
    public void setCode(String code) {
        this.code = code;
    }
    public void setOpenid(String openid){
        this.openid = openid;
    }
    @Override
    public Authorize getFromWx() throws Exception {
        Authorize authorize = oauthService.getAuthorizeByCode(code);
        logger.debug("get authorize from wx,the object is {}",authorize);
        return authorize;
    }

    @Override
    public Authorize getFromDatabase(String token) {
        Authorize authorize = wxAuthorizeService.findByToken(token);
        logger.debug("get authorize from database,the object is {}",authorize);
        return authorize;
    }

    @Override
    public Token saveToDatabase(Authorize t , String tokenKey) throws Exception {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, Integer.parseInt(t.getExpires_in())-100);
        Token token = new Token();
        token.setStatus(1);
        token.setInvalidDate(new DateTime(calendar.getTime()));
        token.setToken_key(tokenKey);
        token = tokenService.add(token);
        t.setToken(token.getId());
       
        wxAuthorizeService.add(t);
        logger.debug("save the Authorize to database,the Authorize is {},the token is {}",t,token.getId());
        return token;
    }

    @Override
    public Authorize getFromDatabaseByOther() {
        Authorize authorize = wxAuthorizeService.findByOpenid(openid);
        return authorize;
    }

    @Override
    public String generateKey(String... args) {
        StringBuffer sb = new StringBuffer();
        sb.append("authorize_");
        sb.append(code+"_");
        for(String arg:args){
            sb.append(arg);
            sb.append("-");
        }
        return sb.toString();
    }


    @Override
    public Authorize getFromDatabaseByKey(String key) {
        Token token = tokenService.findByKeyAndInvalidDateGreaterThan(key, new DateTime());
        if(token!=null){
            Authorize authorize = wxAuthorizeService.findByToken(token.getId());
            return authorize;
        }
        return null;
    }

}
