package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.repositories.WxAuthorizeRepository;
import com.wyc.wx.domain.Authorize;

@Service
public class WxAuthorizeService {
    @Autowired
    private WxAuthorizeRepository wxAuthorizeRepository;
    public void add(Authorize authorize){
        wxAuthorizeRepository.save(authorize);
    }
    
    public void save(Authorize authorize){
        wxAuthorizeRepository.save(authorize);
    }
    
    public Authorize findByToken(String token){
        return wxAuthorizeRepository.findByToken(token);
    }
}
