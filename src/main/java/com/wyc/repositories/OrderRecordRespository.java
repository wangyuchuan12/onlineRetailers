package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.OrderRecord;

public interface OrderRecordRespository extends CrudRepository<OrderRecord, String>{

}
