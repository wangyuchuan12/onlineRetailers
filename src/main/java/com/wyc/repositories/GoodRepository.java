package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.Good;

public interface GoodRepository extends CrudRepository<Good, String>{

    Iterable<Good> findAllByStatusOrderByRankAsc(int status);
}
