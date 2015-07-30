package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.City;
import com.wyc.repositories.CityRepository;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;
    public void add(City city){
        
        cityRepository.save(city);
    }
    
    public void update(City city){
        cityRepository.save(city);
    }
    
    public void delete(City city){
        cityRepository.delete(city);
    }
    
    public void findOne(String id){
        cityRepository.findOne(id);
    }
    
    public Iterable<City> findAll(){
        return cityRepository.findAll();
    }
    
    public Iterable<City> findAllByParentId(String parentId){
        return cityRepository.findAllByParentId(parentId);
    }
}
