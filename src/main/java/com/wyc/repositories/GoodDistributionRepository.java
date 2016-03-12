package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.GoodDistribution;

public interface GoodDistributionRepository extends CrudRepository<GoodDistribution, String>{
    public Iterable<GoodDistribution> findAllByGoodIdAndType(String goodId,int type);
    public Iterable<GoodDistribution> findAllByGoodIdAndParentCity(String goodId , String parentCity);
}
