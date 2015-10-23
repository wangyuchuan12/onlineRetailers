package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GroupPartake;
import com.wyc.repositories.GroupPartakeRepository;

@Service
public class GroupPartakeService {
    @Autowired
    private GroupPartakeRepository groupPartakeRepository;
    public GroupPartake add(GroupPartake groupPartake){
        groupPartake.setId(UUID.randomUUID().toString());
        groupPartake.setCreateAt(new DateTime());
        groupPartake.setUpdateAt(new DateTime());
        return groupPartakeRepository.save(groupPartake);
    }
    
    public GroupPartake save(GroupPartake groupPartake){
        groupPartake.setUpdateAt(new DateTime());
        return groupPartakeRepository.save(groupPartake);
    }
    
    public Iterable<GroupPartake> findByCustomerid(String customerId){
        return groupPartakeRepository.findByCustomerid(customerId);
    }
    
    public GroupPartake findByCustomeridAndGroupId(String customerId , String groupId){
        return groupPartakeRepository.findByCustomeridAndGroupId(customerId , groupId);
    }

    public Iterable<GroupPartake> findAllByGroupIdOrderByRoleAsc(String id) {
        // TODO Auto-generated method stub
        return groupPartakeRepository.findAllByGroupIdOrderByRoleAsc(id);
    }
    
    public int countByGroupId(String groupId){
    	return groupPartakeRepository.countByGroupId(groupId);
    }
}
