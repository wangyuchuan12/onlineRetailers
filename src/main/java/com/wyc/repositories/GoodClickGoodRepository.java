package com.wyc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.domain.GoodClickGood;

public interface GoodClickGoodRepository extends CrudRepository<GoodClickGood, String>{

	GoodClickGood findByOpenid(String openId);
	@Query("select count(*) from com.wyc.domain.GoodClickGood g where g.goodId=:goodId")
	int countByGoodId(@Param("goodId")String goodId);
	GoodClickGood findByOpenidAndGoodId(String openid, String goodId);

}
