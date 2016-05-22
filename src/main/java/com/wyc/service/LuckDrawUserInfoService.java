package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.LuckDrawUserInfo;
import com.wyc.repositories.LuckDrawUserInfoRepository;

@Service
public class LuckDrawUserInfoService {
    @Autowired
    private LuckDrawUserInfoRepository luckDrawUserInfoRepository;
    public LuckDrawUserInfo findByOpenid(String openid) {
        return luckDrawUserInfoRepository.findByOpenid(openid);
    }
    public LuckDrawUserInfo add(LuckDrawUserInfo luckDrawUserInfo) {
        luckDrawUserInfo.setCount(0);
        luckDrawUserInfo.setCreateAt(new DateTime());
        luckDrawUserInfo.setUpdateAt(new DateTime());
        luckDrawUserInfo.setId(UUID.randomUUID().toString());
        return luckDrawUserInfoRepository.save(luckDrawUserInfo);
    }
    public LuckDrawUserInfo save(LuckDrawUserInfo luckDrawUserInfo) {
        luckDrawUserInfo.setUpdateAt(new DateTime());
        return luckDrawUserInfoRepository.save(luckDrawUserInfo);
    }

}
