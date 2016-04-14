package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.DialogSession;
import com.wyc.repositories.DialogSessionRepository;

@Service
public class DialogSessionService {
    @Autowired
    private DialogSessionRepository dialogSessionRepository;
    public DialogSession findByCustomerIdAndAdminId(String customerId, String adminId) {
        return dialogSessionRepository.findByCustomerIdAndAdminId(customerId,adminId);
    }
    public DialogSession add(DialogSession dialogSession) {
        dialogSession.setCreateAt(new DateTime());
        dialogSession.setUpdateAt(new DateTime());
        dialogSession.setId(UUID.randomUUID().toString());
        return dialogSessionRepository.save(dialogSession);
    }
    public Iterable<DialogSession> findAllByCustomerId(String customerId) {
        return dialogSessionRepository.findAllByCustomerId(customerId);
    }

}
