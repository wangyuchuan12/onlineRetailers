package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.Business;
import com.wyc.repositories.BusinessRepository;

@Service
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;

    public Business findByAdminId(String adminId) {
        return businessRepository.findByAdminId(adminId);
    }
}
