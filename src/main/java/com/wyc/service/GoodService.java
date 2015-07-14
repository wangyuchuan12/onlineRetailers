package com.wyc.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wyc.domain.Good;
import com.wyc.repositories.GoodRepository;

@Service
public class GoodService {
    @Autowired
    private GoodRepository goodRepository;
    
    public void add(Good good){
        good.setCreateAt(new DateTime());
        goodRepository.save(good);
    }
    
    public Iterable<Good> findAll(){
        return goodRepository.findAll();
    }
    
}
