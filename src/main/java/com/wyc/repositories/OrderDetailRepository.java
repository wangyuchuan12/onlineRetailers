package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.OrderDetail;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, String>{
    public OrderDetail findByOrderId(String orderId);
    
    public OrderDetail findByGroupId(String groupId);
}
