package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.OrderDetail;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, String>{
    public Iterable<OrderDetail> findAllByOrderId(String orderId);
    
    public Iterable<OrderDetail> findAllByGroupId(String groupId);
}
