package com.wyc.repositories;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.OrderDetail;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, String>{
    public OrderDetail findByOrderId(String orderId);
    
    public OrderDetail findByGroupId(String groupId);

    public Iterable<OrderDetail> findAllByCustomerId(String customerId);

    public Iterable<OrderDetail> findByCustomerIdAndStatusIn(String id,
            Collection<Integer> statuses);

    public OrderDetail findByOutTradeNo(String outTradeNo);
}
