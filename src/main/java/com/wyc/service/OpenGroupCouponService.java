package com.wyc.service;


import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.OpenGroupCoupon;
import com.wyc.repositories.OpenGroupCouponRepository;

@Service
public class OpenGroupCouponService {
    @Autowired
    private OpenGroupCouponRepository openGroupCouponRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    public Iterable<OpenGroupCoupon> findAllByCustomerId(String customerId){
        return openGroupCouponRepository.findAllByCustomerId(customerId);
    }
    
    public int countByCustomerIdAndGoodIdAndEndTimeBeforeAndStatus
    (String customerId ,String goodId,DateTime beforeTime,int status){
        return openGroupCouponRepository.countByCustomerIdAndGoodIdAndEndTimeBeforeAndStatus(customerId, goodId, beforeTime,status);
    }

    public OpenGroupCoupon save(OpenGroupCoupon openGroupCoupon) {
        openGroupCoupon.setUpdateAt(new DateTime());
        return openGroupCouponRepository.save(openGroupCoupon);
    }
    
    public OpenGroupCoupon getFirstRecord(String customerId ,String goodId,DateTime beforeTime,int status){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("select g from com.wyc.domain.OpenGroupCoupon g where g.customerId = :customerId and g.goodId = :goodId and g.endTime>:beforeTime and status=:status", OpenGroupCoupon.class);
        query.setFirstResult(0);
        query.setMaxResults(1);
        query.setParameter("goodId", goodId);
        query.setParameter("customerId", customerId);
        query.setParameter("beforeTime", beforeTime);
        query.setParameter("status", status);
        if(query.getResultList()==null||query.getResultList().size()==0){
        	return null;
        }
        return (OpenGroupCoupon)query.getResultList().get(0);
    }

    public OpenGroupCoupon add(OpenGroupCoupon openGroupCoupon) {
       openGroupCoupon.setUpdateAt(new DateTime());
       openGroupCoupon.setCreateAt(new DateTime());
       openGroupCoupon.setId(UUID.randomUUID().toString());
       return openGroupCouponRepository.save(openGroupCoupon);
    }

    public Iterable<OpenGroupCoupon> findAllByCustomerIdOrderByBeginTimeDesc(
            String customerId) {
        return openGroupCouponRepository.findAllByCustomerIdOrderByBeginTimeDesc(customerId);
    }

    public OpenGroupCoupon findOne(String targetId) {
        return openGroupCouponRepository.findOne(targetId);
    }
}
