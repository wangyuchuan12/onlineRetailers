package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.DialogSessionItemRead;

public interface DialogSessionItemReadRepository extends CrudRepository<DialogSessionItemRead, String>{

    DialogSessionItemRead findByCustomerIdAndRoleAndItemId(String customerId,
            int customerRole, String itemId);

}
