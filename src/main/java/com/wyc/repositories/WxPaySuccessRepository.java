package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.wx.response.domain.PaySuccess;

public interface WxPaySuccessRepository extends CrudRepository<PaySuccess, String>{
    public PaySuccess findByOutTradeNo(String outTradeNo);
}
