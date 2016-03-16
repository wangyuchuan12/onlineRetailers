package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.GroupPartakePayment;

public interface GroupPartakePaymentRepository extends CrudRepository<GroupPartakePayment, String>{

    GroupPartakePayment findByGroupPartakeId(String groupPartakeId);

}
