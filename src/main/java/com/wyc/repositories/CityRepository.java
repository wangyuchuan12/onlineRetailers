package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.City;

public interface CityRepository extends CrudRepository<City, String>{
    public Iterable<City> findAllByParentId(String parentId);

    public Iterable<City> findAllByName(String name);

    public Iterable<City> findAllByCodeAndType(String goodDistributionCode,int type);
}
