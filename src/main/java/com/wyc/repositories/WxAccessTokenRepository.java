package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.wx.domain.AccessTokenBean;

public interface WxAccessTokenRepository extends CrudRepository<AccessTokenBean, String>{
    public AccessTokenBean findByToken(String token);

    public AccessTokenBean findByAccessToken(String accessToken);
}
