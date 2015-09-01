package com.wyc.repositories;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.domain.OpenGroupCoupon;

public interface OpenGroupCouponRepository extends CrudRepository<OpenGroupCoupon, String>{

    public Iterable<OpenGroupCoupon> findAllByCustomerId(String customerId);
    @Query("select count(*) from com.wyc.domain.OpenGroupCoupon g where g.customerId = :customerId and g.goodId = :goodId and g.endTime>:beforeTime and status=:status")
    public int countByCustomerIdAndGoodIdAndEndTimeBeforeAndStatus(@Param("customerId")String customerId ,@Param("goodId")String goodId,@Param("beforeTime")DateTime beforeTime,@Param("status")int status);
}
