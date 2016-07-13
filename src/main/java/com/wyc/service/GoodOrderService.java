package com.wyc.service;

import java.util.Collection;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GoodOrder;
import com.wyc.repositories.GoodOrderRepository;

@Service
public class GoodOrderService {
    @Autowired
   private GoodOrderRepository goodOrderRepository;
   
   public Iterable<GoodOrder> findAllOrderByCreateTimeDesc(Iterable<String> ids){
       return goodOrderRepository.findAllByIdInOrderByCreateTimeDesc(ids);
   }
   
   public GoodOrder add(GoodOrder goodOrder){
	   goodOrder.setId(UUID.randomUUID().toString());
	   goodOrder.setCreateAt(new DateTime());
	   goodOrder.setUpdateAt(new DateTime());
       return goodOrderRepository.save(goodOrder);
   }
   
   public GoodOrder save(GoodOrder goodOrder){
       goodOrder.setUpdateAt(new DateTime());
	   return goodOrderRepository.save(goodOrder);
   }

    public Iterable<GoodOrder> findAllOrderByCreateTimeDesc() {
        return goodOrderRepository.findAll();
        
    }
    
    public Iterable<GoodOrder> findAllByStatusInOrderByCreateTimeDesc(Collection<Integer> statuses){
        return goodOrderRepository.findAllByStatusInOrderByCreateTimeDesc(statuses);
    }
    
    public Iterable<GoodOrder> findAllByStatusOrderByCreateTimeDesc(int status){
        return goodOrderRepository.findAllByStatusOrderByCreateTimeDesc(status);
    }

    public GoodOrder findOne(String orderId) {
        return goodOrderRepository.findOne(orderId);
    }
}
