package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.SystemCity;

public interface CityRepository extends CrudRepository<SystemCity, String>{
    public Iterable<SystemCity> findAllByParentId(String parentId);

    public Iterable<SystemCity> findAllByName(String name);

    public Iterable<SystemCity> findAllByCodeAndType(String goodDistributionCode,int type);
}
