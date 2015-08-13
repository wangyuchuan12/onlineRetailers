package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.Source;
import com.wyc.repositories.SourceRepository;

@Service
public class SourceService {
    @Autowired
    private SourceRepository sourceRepository;
    
    public Source findOne(String id){
        return sourceRepository.findOne(id);
    }
    
    public Source add(Source source){
        source.setId(UUID.randomUUID().toString());
        source.setCreateAt(new DateTime());
        source.setUpdateAt(new DateTime());
        return sourceRepository.save(source);
    }
    
    public Source save(Source source){
        source.setUpdateAt(new DateTime());
        return sourceRepository.save(source);
    }
}
