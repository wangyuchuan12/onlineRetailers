package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.DialogSessionItem;

public interface DialogSessionItemRepository extends CrudRepository<DialogSessionItem, String>{

    Iterable<DialogSessionItem> findAllByDialogSessionId(String dialogSessionId);

    Iterable<DialogSessionItem> findAllByDialogSessionIdOrderByDateTimeAsc(
            String dialogSessionId);

    Iterable<DialogSessionItem> findAllByDialogSessionIdAndRoleOrderByDateTimeAsc(
            String dialogSessionId, int role);

}
