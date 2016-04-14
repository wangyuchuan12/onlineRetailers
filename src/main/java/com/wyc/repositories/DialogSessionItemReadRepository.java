package com.wyc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.domain.DialogSessionItemRead;

public interface DialogSessionItemReadRepository extends CrudRepository<DialogSessionItemRead, String>{

    DialogSessionItemRead findByCustomerIdAndRoleAndItemId(String customerId,
            int customerRole, String itemId);
    
    @Query("select count(*) from com.wyc.domain.DialogSessionItemRead g where g.customerId=:customerId and g.adminId=:adminId and g.role=:role and g.count>:count")
    int countByCustomerIdAndAdminIdAndRoleAndCountGreaterThan(
            @Param("customerId")String customerId, @Param("adminId")String adminId, @Param("role")int role, @Param("count")int count);

}
