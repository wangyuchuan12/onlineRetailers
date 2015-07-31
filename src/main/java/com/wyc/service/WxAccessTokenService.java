package com.wyc.service;

import java.util.UUID;

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
        wxAccessTokenRepository.save(accessTokenBean);
    }
    
    public void save(AccessTokenBean accessTokenBean){
        wxAccessTokenRepository.save(accessTokenBean);
    }
    
    public AccessTokenBean findByToken(String token){
        return wxAccessTokenRepository.findByToken(token);
    }
}
