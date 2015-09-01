package com.wyc.service;


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
        return (OpenGroupCoupon)query.getResultList().get(0);
    }
}
