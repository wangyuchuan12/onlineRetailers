package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.GroupSuccessGiveOpenGroupCouponActivity;

public interface GroupSuccessGiveOpenGroupCouponActivityRepository extends CrudRepository<GroupSuccessGiveOpenGroupCouponActivity, String>{

    Iterable<GroupSuccessGiveOpenGroupCouponActivity> findAllByActivityId(
            String activityId);

}
