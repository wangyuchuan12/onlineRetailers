package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wyc.domain.TempGroupOrder;
import com.wyc.repositories.TempGroupOrderRepository;

@Service
public class TempGroupOrderService {
    @Autowired
    private TempGroupOrderRepository tempGroupOrderRepository;
    public TempGroupOrder add(TempGroupOrder tempGroupOrder){
        tempGroupOrder.setId(UUID.randomUUID().toString());
        tempGroupOrder.setCreateAt(new DateTime());
        tempGroupOrder.setUpdateAt(new DateTime());
        return tempGroupOrderRepository.save(tempGroupOrder);
    }
    
    public TempGroupOrder save(TempGroupOrder tempGroupOrder){
        tempGroupOrder.setUpdateAt(new DateTime());
        return tempGroupOrderRepository.save(tempGroupOrder);
    }
    
    public TempGroupOrder findByOutTradeNo(String outTradeNo){
        return tempGroupOrderRepository.findByOutTradeNo(outTradeNo);
    }
}
