package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.repositories.WxContextRepository;
import com.wyc.wx.domain.WxContext;

@Service
public class WxContextService {
    @Autowired
    private WxContextRepository wxContextRepository;
    public WxContext getWxContextBean(){
        Iterable<WxContext> wxContexts = wxContextRepository.findAll();
        for(WxContext wxContext:wxContexts){
            return wxContext;
        }
        return null;
    }
}
