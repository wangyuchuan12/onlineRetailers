package com.wyc.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.GoodView;

public interface GoodViewRepository extends CrudRepository<GoodView, String>{

	
	List<GoodView> findAllByGoodTypeId(String goodTypeId);

}
