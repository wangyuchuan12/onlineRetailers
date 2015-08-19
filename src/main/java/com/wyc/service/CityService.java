package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.City;
import com.wyc.repositories.CityRepository;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;
    public void add(City city){
        city.setId(UUID.randomUUID().toString());
        city.setCreateAt(new DateTime());
        city.setUpdateAt(new DateTime());
        cityRepository.save(city);
    }
    
    public void update(City city){
        cityRepository.save(city);
    }
    
    public void delete(City city){
        cityRepository.delete(city);
    }
    
    public City findOne(String id){
        return cityRepository.findOne(id);
    }
    
    public Iterable<City> findAll(){
        return cityRepository.findAll();
    }
    
    public Iterable<City> findAllByParentId(String parentId){
        return cityRepository.findAllByParentId(parentId);
    }
}
