package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.repositories.WxUserInfoRepository;
import com.wyc.wx.domain.UserInfo;

@Service
public class WxUserInfoService {
    @Autowired
    private WxUserInfoRepository userInfoRepository;
    public void add(UserInfo userInfo){
        userInfoRepository.save(userInfo);
    }
    
    public void save(UserInfo userInfo){
        userInfoRepository.save(userInfo);
    }
    
    public UserInfo findByToken(String token){
        return userInfoRepository.findByToken(token);
    }
}
