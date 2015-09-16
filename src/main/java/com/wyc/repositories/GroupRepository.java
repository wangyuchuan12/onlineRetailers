package com.wyc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.GoodGroup;
import com.wyc.domain.GroupPartake;

public interface GroupRepository extends CrudRepository<GroupPartake, String>{
    
}
