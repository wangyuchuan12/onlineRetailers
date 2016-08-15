package com.wyc.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.Good;
import com.wyc.repositories.GoodRepository;

@Service
public class GoodService {
    final static Logger logger = LoggerFactory.getLogger(GoodService.class);
    @Autowired
    private GoodRepository goodRepository;
    
    public void add(Good good){
        good.setCreateAt(new DateTime());
        good.setUpdateAt(new DateTime());
        good.setId(UUID.randomUUID().toString());
        goodRepository.save(good);
    }
    
    public Iterable<Good> findAll(){
        return goodRepository.findAll();
    }
    
    public Iterable<Good> findAllByStatusOrderByRank(int status){
        return goodRepository.findAllByStatusOrderByRankAsc(status);
    }
    
    public Good findOne(String id){
        logger.debug("findOne by id {}",id);
        return goodRepository.findOne(id);
    }
    
    public void delete(String id){
        logger.debug("delete by id {}",id);
        goodRepository.delete(id);
    }

    public void save(Good good) {
        good.setUpdateAt(new DateTime());
        goodRepository.save(good);
        
    }

    public Iterable<Good> findAllByStatusAndGoodTypeOrderByRank(int status,
            String goodTypeId) {
        return goodRepository.findAllByStatusAndGoodTypeOrderByRankAsc(status,goodTypeId);
    }

    public Iterable<Good> findAllByGoodTypeAndIsDisplayMainOrderByRank(
            String goodTypeId, boolean b) {
        return goodRepository.findAllByGoodTypeAndIsDisplayMainOrderByRankAsc(goodTypeId,b);
    }

	public Iterable<Good> findAllByGoodTypeAndUnionIdsAndIsDisplayMainOrderByRank(String goodTypeId, String[] goods,
			boolean b) {
		return goodRepository.findAllByGoodTypeAndUnionIdsAndIsDisplayMainOrderByRank(goodTypeId,goods,b);
	}
    
}
