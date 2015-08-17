package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.OrderDetail;
import com.wyc.repositories.OrderDetailRepository;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    public OrderDetail add(OrderDetail orderDetail){
    	orderDetail.setCreateAt(new DateTime());
    	orderDetail.setUpdateAt(new DateTime());
    	orderDetail.setId(UUID.randomUUID().toString());
    	return orderDetailRepository.save(orderDetail);
    }
    
    public OrderDetail save(OrderDetail orderDetail){
    	orderDetail.setUpdateAt(new DateTime());
    	return orderDetailRepository.save(orderDetail);
    }
    
    public OrderDetail findOne(String id){
    	return orderDetailRepository.findOne(id);
    }
    
    public OrderDetail findByOrderId(String orderId){
    	return orderDetailRepository.findByOrderId(orderId);
    }
    
    public OrderDetail findByGruopId(String groupId){
    	return orderDetailRepository.findByGroupId(groupId);
    }
}
