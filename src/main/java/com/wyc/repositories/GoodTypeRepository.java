package com.wyc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.SystemGoodType;

public interface GoodTypeRepository extends CrudRepository<SystemGoodType, String>{

    List<SystemGoodType> findAllByIsDefault(boolean isDefault);

}
