package com.wyc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.GoodType;

public interface GoodTypeRepository extends CrudRepository<GoodType, String>{

    List<GoodType> findAllByIsDefault(boolean isDefault);

}
