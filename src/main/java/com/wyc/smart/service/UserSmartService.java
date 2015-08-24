package com.wyc.smart.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.defineBean.ApplicationProperties;
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
    @Autowired
    private ApplicationProperties applicationProperties;
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
    public Token saveToDatabase(UserInfo t , String tokenKey) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR,Integer.parseInt(applicationProperties.getProperty("token_time_long_hour")));
        calendar.add(Calendar.MINUTE,Integer.parseInt(applicationProperties.getProperty("token_time_long_min")));
        calendar.add(Calendar.SECOND,Integer.parseInt(applicationProperties.getProperty("token_time_long_second")));
        Token token = tokenService.findByTokenKey(tokenKey);
        if(token==null){
            token = new Token();
            token.setStatus(1);
            token.setInvalidDate(new DateTime(calendar.getTime()));
            token.setTokenKey(tokenKey);
            token = tokenService.add(token);
        }else{
            token.setStatus(1);
            token.setInvalidDate(new DateTime(calendar.getTime()));
            token.setTokenKey(tokenKey);
            token = tokenService.save(token);
        }
       
        UserInfo userInfo = wxUserInfoService.findByToken(token.getId());
        if(userInfo==null){
            t.setToken(token.getId());
            userInfo = wxUserInfoService.findByOpenid(t.getOpenid());
            if(userInfo!=null){
                t.setId(userInfo.getId());
                wxUserInfoService.save(t);
            }else{
                wxUserInfoService.add(t);
            }
            
        }else{
            userInfo.setCity(t.getCity());
            userInfo.setCountry(t.getCountry());
            userInfo.setGroupid(t.getGroupid());
            userInfo.setHeadimgurl(t.getHeadimgurl());
            userInfo.setLanguage(t.getLanguage());
            userInfo.setNickname(t.getNickname());
            userInfo.setOpenid(t.getOpenid());
            userInfo.setPrivilege(t.getPrivilege());
            userInfo.setProvince(t.getProvince());
            userInfo.setRemark(t.getRemark());
            userInfo.setSex(t.getSex());
            userInfo.setSubscribe(t.getSubscribe());
            userInfo.setSubscribe_time(t.getSubscribe_time());
            userInfo.setUnionid(t.getUnionid());
            userInfo.setToken(token.getId());
            wxUserInfoService.save(userInfo);
        }
        
        logger.debug("save the userInfo to database,the UserInfo is {},the token is {}",t,token.getId());
        return token;
    }

    @Override
    public String generateKey(String ... args) {
        if(code==null||code.trim().equals("")){
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("userInfo_");
        sb.append(code+"_");
        for(String arg:args){
            sb.append(arg);
            sb.append("-");
        }
        return sb.toString();
    }


    @Override
    public UserInfo getFromDatabaseByKey(String key) {
        Token token = tokenService.findByKeyAndInvalidDateGreaterThan(key, new DateTime());
        if(token!=null){
            UserInfo userinfo = wxUserInfoService.findByToken(token.getId());
            return userinfo;
        }
        return null;
    }

    
}
