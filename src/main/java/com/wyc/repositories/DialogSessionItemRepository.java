package com.wyc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.domain.DialogSessionItem;

public interface DialogSessionItemRepository extends CrudRepository<DialogSessionItem, String>{

    Iterable<DialogSessionItem> findAllByDialogSessionId(String dialogSessionId);

    Iterable<DialogSessionItem> findAllByDialogSessionIdOrderByDateTimeAsc(
            String dialogSessionId);

    Iterable<DialogSessionItem> findAllByDialogSessionIdAndRoleOrderByDateTimeAsc(
            String dialogSessionId, int role);

    Iterable<DialogSessionItem> findAllByCustomerIdAndRoleOrderByDateTimeAsc(
            String customerId, int role);

    @Query("select count(*) from com.wyc.domain.DialogSessionItem g where g.dialogSessionId=:dialogSessionId and g.role=:role")
    int countByDialogSessionIdAndRole(@Param("dialogSessionId")String dialogSessionId, @Param("role")int role);

    Iterable<DialogSessionItem> findAllByCustomerIdAndDialogSessionIdAndRole(
            String customerId, String dialogSessionId, int role);
    
    @Query("select g from com.wyc.domain.DialogSessionItem g where g.recordIndex = (select max(recordIndex) from com.wyc.domain.DialogSessionItem) and g.dialogSessionId=:dialogSessionId")
    DialogSessionItem selectLastItemByDialogSessionId(@Param("dialogSessionId")String dialogSessionId);
    
    @Query("select max(g.recordIndex) from com.wyc.domain.DialogSessionItem g")
    Integer getMaxIndex();

}
