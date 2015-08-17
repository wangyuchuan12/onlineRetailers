package com.wyc.repositories;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.GoodOrder;

public interface GoodOrderRepository extends CrudRepository<GoodOrder, String>{

    public Iterable<GoodOrder> findAllByStatusIn(Collection<Integer> statuses);

    public Iterable<GoodOrder> findAllByStatusOrderByCreateTimeDesc(int status);

}
