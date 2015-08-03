package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.wx.domain.UserInfo;

public interface WxUserInfoRepository extends CrudRepository<UserInfo, String>{
    public UserInfo findByToken(String token);

    public UserInfo findByOpenid(String openid);
}
