package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.GroupPartake;

public interface GroupPartakeRepository extends CrudRepository<GroupPartake, String>{

    public Iterable<GroupPartake> findByCustomerid(String customerId);

    public Iterable<GroupPartake> findAllByGroupId(String id);

}
