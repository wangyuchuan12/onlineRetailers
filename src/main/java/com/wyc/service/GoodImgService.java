package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.wyc.domain.GoodImg;
import com.wyc.repositories.GoodImgRepository;

@Service
public class GoodImgService {
    private GoodImgRepository goodImgRepository;
    public void add(GoodImg goodImg){
        goodImgRepository.save(goodImg);
    }
    public void save(GoodImg goodImg){
        goodImgRepository.save(goodImg);
    }
    
    public Iterable<GoodImg> findAllByGoodIdOrderByLevel(String goodId){
        return goodImgRepository.findAllByGoodIdOrderByLevelDesc(goodId);
    }
    
    public void delete(GoodImg goodImg){
        goodImgRepository.delete(goodImg);
    }
}
