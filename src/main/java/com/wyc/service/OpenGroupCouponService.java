package com.wyc.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.OpenGroupCoupon;
import com.wyc.repositories.OpenGroupCouponRepository;

@Service
public class OpenGroupCouponService {
    @Autowired
    private OpenGroupCouponRepository openGroupCouponRepository;
    
    public Iterable<OpenGroupCoupon> findAllByCustomerId(String customerId){
        return openGroupCouponRepository.findAllByCustomerId(customerId);
    }
    
    public int countByCustomerIdAndGoodIdAndEndTimeBefore
    (String customerId ,String goodId,DateTime beforeTime){
        return openGroupCouponRepository.countByCustomerIdAndGoodIdAndEndTimeBefore(customerId, goodId, beforeTime);
    }

    public OpenGroupCoupon save(OpenGroupCoupon openGroupCoupon) {
        openGroupCoupon.setUpdateAt(new DateTime());
        return openGroupCouponRepository.save(openGroupCoupon);
    }
}
