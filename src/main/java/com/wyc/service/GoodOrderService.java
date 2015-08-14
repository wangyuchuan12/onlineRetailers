package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GoodOrder;
import com.wyc.repositories.GoodOrderRepository;

@Service
public class GoodOrderService {
    @Autowired
   private GoodOrderRepository goodOrderRepository;
   
   public Iterable<GoodOrder> findAll(Iterable<String> ids){
       return goodOrderRepository.findAll(ids);
   }
   
   public void add(GoodOrder goodOrder){
       goodOrderRepository.save(goodOrder);
   }
   
   public void save(GoodOrder goodOrder){
       goodOrderRepository.save(goodOrder);
   }

    public Iterable<GoodOrder> findAll() {
        return goodOrderRepository.findAll();
        
    }
}
