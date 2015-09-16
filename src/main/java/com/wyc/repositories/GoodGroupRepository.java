package com.wyc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.GoodGroup;

public interface GoodGroupRepository extends CrudRepository<GoodGroup, String>{
    @Query("select g from com.wyc.domain.GoodGroup g where g.createAt = select max(g2.createAt) from com.wyc.domain.GoodGroup g2")
    public GoodGroup selectLastestGoodGroup();
}
