package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.repositories.BusinessRepository;

@Service
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;
}
