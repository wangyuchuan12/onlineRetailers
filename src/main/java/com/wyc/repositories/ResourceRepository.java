package com.wyc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.domain.MyResource;

public interface ResourceRepository extends CrudRepository<MyResource, String>{

	@Query("select a from com.wyc.domain.MyResource a , com.wyc.domain.GoodImg b where a.id = b.imgId and b.goodId=:goodId order by b.level desc")
	Iterable<MyResource> findAllByGoodId(@Param("goodId")String goodId);

}
