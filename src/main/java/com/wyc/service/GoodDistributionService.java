package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GoodDistribution;
import com.wyc.repositories.GoodDistributionRepository;

@Service
public class GoodDistributionService {
    @Autowired
    private GoodDistributionRepository goodDistributionRepository;
    
    public GoodDistribution save(GoodDistribution goodDistribution){
        goodDistribution.setUpdateAt(new DateTime());
        return goodDistributionRepository.save(goodDistribution);
    }
    
    public GoodDistribution add(GoodDistribution goodDistribution){
        goodDistribution.setId(UUID.randomUUID().toString());
        goodDistribution.setCreateAt(new DateTime());
        goodDistribution.setUpdateAt(new DateTime());
        return goodDistributionRepository.save(goodDistribution);
    }
    
    public GoodDistribution findOne(String id){
        return goodDistributionRepository.findOne(id);
    }
    
    public Iterable<GoodDistribution> findAllByGoodIdAndType(String goodId,int type){
        return goodDistributionRepository.findAllByGoodIdAndType(goodId, type);
    }
    
    public Iterable<GoodDistribution> findAllByGoodIdAndParentCity(String goodId , String parentCity){
        return goodDistributionRepository.findAllByGoodIdAndParentCity(goodId, parentCity);
    }
}
