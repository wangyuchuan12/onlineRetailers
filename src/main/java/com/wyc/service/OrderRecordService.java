package com.wyc.service;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.OrderRecord;
import com.wyc.repositories.OrderRecordRespository;

@Service
public class OrderRecordService {
    @Autowired
    private OrderRecordRespository orderRecordRespository;
    
    public OrderRecord add(OrderRecord orderRecord){
        orderRecord.setId(UUID.randomUUID().toString());
        orderRecord.setCreateAt(new DateTime());
        orderRecord.setUpdateAt(new DateTime());
        return orderRecordRespository.save(orderRecord);
    }
    
    public OrderRecord save(OrderRecord orderRecord){
        orderRecord.setUpdateAt(new DateTime());
        return orderRecordRespository.save(orderRecord);
    }

    public Iterable<OrderRecord> findAllByOrderId(String orderId) {
        return orderRecordRespository.findAllByOrderId(orderId);
    }
}
