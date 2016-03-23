package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GroupSuccessGiveOpenGroupCouponActivity;
import com.wyc.repositories.GroupSuccessGiveOpenGroupCouponActivityRepository;

@Service
public class GroupSuccessGiveOpenGroupCouponActivityService {
    @Autowired
    private GroupSuccessGiveOpenGroupCouponActivityRepository groupSuccessGiveOpenGroupCouponActivityRepository;

    public Iterable<GroupSuccessGiveOpenGroupCouponActivity> findAllByActivityId(
            String activityId) {
        return groupSuccessGiveOpenGroupCouponActivityRepository.findAllByActivityId(activityId);
    }
}
