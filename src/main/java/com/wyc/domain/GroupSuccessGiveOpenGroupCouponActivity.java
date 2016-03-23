package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "group_success_activity")
public class GroupSuccessGiveOpenGroupCouponActivity {
    
    @Id
    private String id;
    @Column(name="open_group_coupon_num")
    private String openGroupCouponNum;
    @Column(name="good_id")
    private String goodId;
    @Column(name="time_long")
    private Long timeLong;
    @Column(name="activity_id")
    private String activityId;
    public String getActivityId() {
        return activityId;
    }
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOpenGroupCouponNum() {
        return openGroupCouponNum;
    }
    public void setOpenGroupCouponNum(String openGroupCouponNum) {
        this.openGroupCouponNum = openGroupCouponNum;
    }
    public String getGoodId() {
        return goodId;
    }
    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }
    public Long getTimeLong() {
        return timeLong;
    }
    public void setTimeLong(Long timeLong) {
        this.timeLong = timeLong;
    }
}
