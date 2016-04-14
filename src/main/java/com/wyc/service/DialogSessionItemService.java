package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.DialogSessionItem;
import com.wyc.repositories.DialogSessionItemRepository;

@Service
public class DialogSessionItemService {
    @Autowired
    private DialogSessionItemRepository dialogSessionItemRepository;
    public Iterable<DialogSessionItem> findAllByDialogSessionId(String dialogSessionId) {
        return dialogSessionItemRepository.findAllByDialogSessionId(dialogSessionId);
    }
    public DialogSessionItem add(DialogSessionItem dialogSessionItem) {
        dialogSessionItem.setId(UUID.randomUUID().toString());
        dialogSessionItem.setCreateAt(new DateTime());
        dialogSessionItem.setUpdateAt(new DateTime());
        Integer maxIndex = dialogSessionItemRepository.getMaxIndex();
        if(maxIndex==null){
            maxIndex=0;
        }
        dialogSessionItem.setRecordIndex(maxIndex+1);
        return dialogSessionItemRepository.save(dialogSessionItem);
        
    }
    public Iterable<DialogSessionItem> findAllByDialogSessionIdOrderByRecordIndexAsc(
            String dialogSessionId) {
        return dialogSessionItemRepository.findAllByDialogSessionIdOrderByRecordIndexAsc(dialogSessionId);
    }
    public Iterable<DialogSessionItem> findAllByDialogSessionIdAndRoleOrderByDateTimeAsc(
            String dialogSessionId, int role) {
        return dialogSessionItemRepository.findAllByDialogSessionIdAndRoleOrderByDateTimeAsc(dialogSessionId,role);
    }
    public Iterable<DialogSessionItem> findAllByCustomerIdAndRole(String customerId,
            int role) {
        return dialogSessionItemRepository.findAllByCustomerIdAndRoleOrderByDateTimeAsc(customerId,role);
    }

    public Iterable<DialogSessionItem> findAllByCustomerIdAndDialogSessionIdAndRole(
            String customerId, String dialogSessionId, int role) {
        return dialogSessionItemRepository.findAllByCustomerIdAndDialogSessionIdAndRole(customerId,dialogSessionId,role);
    }
    public DialogSessionItem selectLastItemByDialogSessionId(String dialogSessionId) {
        return dialogSessionItemRepository.selectLastItemByDialogSessionId(dialogSessionId);
    }

}
