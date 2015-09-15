package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.repositories.WxPaySuccessRepository;
import com.wyc.wx.response.domain.PaySuccess;

@Service
public class WxPaySuccessService {
    @Autowired
    private WxPaySuccessRepository wxPaySuccessRepository;
    
    public PaySuccess findByOutTradeNo(String outTradeNo){
        return wxPaySuccessRepository.findByOutTradeNo(outTradeNo);
    }
    
    public PaySuccess add(PaySuccess paySuccess){
        paySuccess.setId(UUID.randomUUID().toString());
        paySuccess.setCreateAt(new DateTime());
        paySuccess.setUpdateAt(new DateTime());
        return wxPaySuccessRepository.save(paySuccess);
    }
    
    public PaySuccess save(PaySuccess paySuccess){
        paySuccess.setUpdateAt(new DateTime());
        return wxPaySuccessRepository.save(paySuccess);
    }
}
