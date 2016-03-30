package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.SystemCity;
import com.wyc.repositories.CityRepository;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;
    public void add(SystemCity city){
        city.setId(UUID.randomUUID().toString());
        city.setCreateAt(new DateTime());
        city.setUpdateAt(new DateTime());
        cityRepository.save(city);
    }
    
    public void update(SystemCity city){
        cityRepository.save(city);
    }
    
    public void delete(SystemCity city){
        cityRepository.delete(city);
    }
    
    public SystemCity findOne(String id){
        return cityRepository.findOne(id);
    }
    
    public Iterable<SystemCity> findAll(){
        return cityRepository.findAll();
    }
    
    public Iterable<SystemCity> findAllByParentId(String parentId){
        return cityRepository.findAllByParentId(parentId);
    }

    public Iterable<SystemCity> findAllByName(String name) {
        return cityRepository.findAllByName(name);
    }

    public Iterable<SystemCity> findAllByCodeAndType(String goodDistributionCode,int type) {
        return cityRepository.findAllByCodeAndType(goodDistributionCode,type);
    }
}
