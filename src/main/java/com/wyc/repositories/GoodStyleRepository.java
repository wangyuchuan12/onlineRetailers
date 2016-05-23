package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.GoodStyle;

public interface GoodStyleRepository extends CrudRepository<GoodStyle, String>{

    Iterable<GoodStyle> findAllByGoodId(String goodId);

}
