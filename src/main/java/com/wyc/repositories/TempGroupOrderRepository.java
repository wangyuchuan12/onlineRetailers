package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.TempGroupOrder;

public interface TempGroupOrderRepository extends CrudRepository<TempGroupOrder, String>{

    public TempGroupOrder findByOutTradeNo(String outTradeNo);

}
