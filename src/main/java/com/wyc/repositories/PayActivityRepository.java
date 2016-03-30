package com.wyc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.SystemPayActivity;

public interface PayActivityRepository extends CrudRepository<SystemPayActivity,String>{

    List<SystemPayActivity> findAllByGoodIdAndPayType(String goodId, int groupType);

}
