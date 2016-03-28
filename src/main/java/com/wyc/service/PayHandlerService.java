package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.repositories.PayHandlerRepository;

@Service
public class PayHandlerService {
    @Autowired
    private PayHandlerRepository payHandlerRepository;

    public Iterable<com.wyc.domain.PayHandler> findAll(Iterable<String> ids) {
        return payHandlerRepository.findAll(ids);
    }
}
