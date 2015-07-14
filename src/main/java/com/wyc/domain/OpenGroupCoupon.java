package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/*
 * 开团优惠卷
 */
@Entity(name = "open_group_coupon")
public class OpenGroupCoupon {
    @Id
    private String id;
    @Column(name = "begin_time")
    private DateTime beginTime;
    @Column(name = "end_time")
    private DateTime endTime;
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "good_id")
    private String goodId;
    //创建者
    @Column(name = "create_manager")
    private String createManager;
    
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    private DateTime updateAt;
    public DateTime getCreateAt() {
        return createAt;
    }
    public void setCreateAt(DateTime createAt) {
        this.createAt = createAt;
    }
    public DateTime getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(DateTime updateAt) {
        this.updateAt = updateAt;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public DateTime getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(DateTime beginTime) {
        this.beginTime = beginTime;
    }
    public DateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getGoodId() {
        return goodId;
    }
    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }
    public String getCreateManager() {
        return createManager;
    }
    public void setCreateManager(String createManager) {
        this.createManager = createManager;
    }
}
