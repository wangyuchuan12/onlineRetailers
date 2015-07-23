package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GoodGroup;
import com.wyc.repositories.GoodGroupRepository;

@Service
public class GoodGroupService {
    @Autowired
    private GoodGroupRepository goodGroupRepository;
    public void add(GoodGroup goodGroup){
        goodGroupRepository.save(goodGroup);
    }
    
    public void save(GoodGroup goodGroup){
        goodGroupRepository.save(goodGroup);
    }
    
    public GoodGroup findOne(String id){
        return goodGroupRepository.findOne(id);
    }
}
