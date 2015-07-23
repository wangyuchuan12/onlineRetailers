package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GroupPartake;
import com.wyc.repositories.GroupPartakeRepository;

@Service
public class GroupPartakeService {
    @Autowired
    private GroupPartakeRepository groupPartakeRepository;
    public void add(GroupPartake groupPartake){
        groupPartakeRepository.save(groupPartake);
    }
    
    public void save(GroupPartake groupPartake){
        groupPartakeRepository.save(groupPartake);
    }
    
    public Iterable<GroupPartake> findByCustomerid(String customerId){
        return groupPartakeRepository.findByCustomerid(customerId);
    }
}
