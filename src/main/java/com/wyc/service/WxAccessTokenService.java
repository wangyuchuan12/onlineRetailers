package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.repositories.WxAccessTokenRepository;
import com.wyc.wx.domain.AccessTokenBean;

@Service
public class WxAccessTokenService {
    @Autowired
    private WxAccessTokenRepository wxAccessTokenRepository;
    public void add(AccessTokenBean accessTokenBean){
        accessTokenBean.setId(UUID.randomUUID().toString());
        accessTokenBean.setCreateAt(new DateTime());
        accessTokenBean.setUpdateAt(new DateTime());
        wxAccessTokenRepository.save(accessTokenBean);
    }
    
    public void save(AccessTokenBean accessTokenBean){
        accessTokenBean.setUpdateAt(new DateTime());
        wxAccessTokenRepository.save(accessTokenBean);
    }
    
    public AccessTokenBean findByToken(String token){
        return wxAccessTokenRepository.findByToken(token);
    }

    public AccessTokenBean findByAccessToken(String accessToken) {

        return wxAccessTokenRepository.findByAccessToken(accessToken);
    }

}
