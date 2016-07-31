package com.wyc.service;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GoodImg;
import com.wyc.repositories.GoodImgRepository;

@Service
public class GoodImgService {
    @Autowired
    private GoodImgRepository goodImgRepository;
    public void add(GoodImg goodImg){
        goodImg.setId(UUID.randomUUID().toString());
        goodImg.setCreateAt(new DateTime());
        goodImg.setUpdateAt(new DateTime());
        goodImgRepository.save(goodImg);
    }
    public void save(GoodImg goodImg){
        goodImg.setUpdateAt(new DateTime());
        goodImgRepository.save(goodImg);
    }
    
    public List<GoodImg> findAllByGoodIdOrderByLevel(String goodId){
        return goodImgRepository.findAllByGoodIdOrderByLevelDesc(goodId);
    }
    
    public void delete(GoodImg goodImg){
        goodImgRepository.delete(goodImg);
    }
    public void delete(String img_id) {
        goodImgRepository.delete(img_id);
        
    }
}
