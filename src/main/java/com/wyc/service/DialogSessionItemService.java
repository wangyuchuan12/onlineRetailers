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
        return dialogSessionItemRepository.save(dialogSessionItem);
        
    }
    public Iterable<DialogSessionItem> findAllByDialogSessionIdOrderByDateTimeAsc(
            String dialogSessionId) {
        return dialogSessionItemRepository.findAllByDialogSessionIdOrderByDateTimeAsc(dialogSessionId);
    }

}
