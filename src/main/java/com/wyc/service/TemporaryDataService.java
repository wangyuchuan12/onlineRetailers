package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.TemporaryData;
import com.wyc.repositories.TemporaryDataRespository;

@Service
public class TemporaryDataService {
    @Autowired
    private TemporaryDataRespository temporaryDataRespository;
    
    public TemporaryData add(TemporaryData temporaryData){
        temporaryData.setCreateAt(new DateTime());
        temporaryData.setUpdateAt(new DateTime());
        temporaryData.setId(UUID.randomUUID().toString());
        return temporaryDataRespository.save(temporaryData);
    }
    
    public TemporaryData save(TemporaryData temporaryData){
        temporaryData.setUpdateAt(new DateTime());
        return temporaryDataRespository.save(temporaryData);
    }
    
    public Iterable<TemporaryData> findAllByKey(String key){
        return temporaryDataRespository.findAllByKey(key);
    }
}
