package com.wyc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.SystemPayActivity;
import com.wyc.repositories.PayActivityRepository;

@Service
public class PayActivityService {
    @Autowired
    private PayActivityRepository payActivityRepository;

    public List<SystemPayActivity> findAllByGoodIdAndPayType(String goodId,
            int groupType) {
        return payActivityRepository.findAllByGoodIdAndPayType(goodId,groupType);
    }
}
