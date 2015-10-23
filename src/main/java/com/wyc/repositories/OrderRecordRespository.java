package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.OrderRecord;
import com.wyc.domain.TempGroupOrder;

public interface OrderRecordRespository extends CrudRepository<OrderRecord, String>{

    public Iterable<OrderRecord> findAllByOrderId(String orderId);

}
