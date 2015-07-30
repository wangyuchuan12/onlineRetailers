package com.wyc.httpdecorate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.wx.domain.Authorize;

@Service
public class DecorateFactory {
    
    @Autowired
    private AutowireCapableBeanFactory factory;
    public AccessTokenDecorate accessTokenDecorate(MyHttpServletRequest httpServletRequest){
        AccessTokenDecorate accessTokenDecorate = new AccessTokenDecorate(httpServletRequest);
        factory.autowireBean(accessTokenDecorate);
        return accessTokenDecorate;
    }
    
    public AuthorizeDecorate authorizeDecorate(MyHttpServletRequest httpServletRequest , String code){
        AuthorizeDecorate authorizeDecorate = new AuthorizeDecorate(httpServletRequest, code);
        factory.autowireBean(authorizeDecorate);
        return authorizeDecorate;
    }
    
    public UserInfoFromWebDecorate infoFromWebDecorate(MyHttpServletRequest myHttpServletRequest,Authorize authorize){
        UserInfoFromWebDecorate userInfoFromWebDecorate = new UserInfoFromWebDecorate(myHttpServletRequest, authorize);
        factory.autowireBean(userInfoFromWebDecorate);
        return userInfoFromWebDecorate;
    }
}
