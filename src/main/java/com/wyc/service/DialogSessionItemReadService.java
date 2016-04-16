package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.DialogSessionItemRead;
import com.wyc.repositories.DialogSessionItemReadRepository;

@Service
public class DialogSessionItemReadService {
    @Autowired
    private DialogSessionItemReadRepository dialogSessionItemReadRepository;

    public DialogSessionItemRead findByCustomerIdAndRoleAndItemId(String customerId,
            int customerRole, String itemId) {
        return dialogSessionItemReadRepository.findByCustomerIdAndRoleAndItemId(customerId,customerRole,itemId);
    }

    public DialogSessionItemRead save(DialogSessionItemRead dialogSessionItemRead) {
        dialogSessionItemRead.setUpdateAt(new DateTime());
        return dialogSessionItemReadRepository.save(dialogSessionItemRead);
        
    }

    public DialogSessionItemRead add(DialogSessionItemRead dialogSessionItemRead) {
        dialogSessionItemRead.setId(UUID.randomUUID().toString());
        dialogSessionItemRead.setUpdateAt(new DateTime());
        dialogSessionItemRead.setCreateAt(new DateTime());
        return dialogSessionItemReadRepository.save(dialogSessionItemRead);
        
    }

    public int countByCustomerIdAndAdminIdAndRoleAndCountGreaterThan(String customerId,
            String adminId, int role, int count) {
        return dialogSessionItemReadRepository.countByCustomerIdAndAdminIdAndRoleAndCountGreaterThan(customerId,
                adminId, role, count);
    }
}
