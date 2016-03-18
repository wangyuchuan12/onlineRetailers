package com.wyc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.PayActivity;

public interface PayActivityRepository extends CrudRepository<PayActivity,String>{

    List<PayActivity> findAllByGoodIdAndPayType(String goodId, int groupType);

}
