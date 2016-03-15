package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GroupPartakePayment;
import com.wyc.repositories.GroupPartakePaymentRepository;

@Service
public class GroupPartakePaymentService {
    @Autowired
    private GroupPartakePaymentRepository groupPartakePaymentRepository;
    public GroupPartakePayment add(GroupPartakePayment groupPartakePayment) {
        groupPartakePayment.setCreateAt(new DateTime());
        groupPartakePayment.setUpdateAt(new DateTime());
        groupPartakePayment.setId(UUID.randomUUID().toString());
        return groupPartakePaymentRepository.save(groupPartakePayment);
        
    }

}
