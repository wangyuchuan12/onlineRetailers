package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GroupPartakePayment;
import com.wyc.repositories.GroupPartakePaymentRepository;

@Service
public class GroupPartakePaymentService {
    @Autowired
    private GroupPartakePaymentRepository groupPartakePaymentRepository;
    public GroupPartakePayment add(GroupPartakePayment groupPartakePayment) {
        return groupPartakePaymentRepository.save(groupPartakePayment);
        
    }

}
