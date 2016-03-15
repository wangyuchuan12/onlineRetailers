package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity(name = "group_partake_deliver")
public class GroupPartakeDeliver {
    @Id
    private String id;
    //物流编号
    @Column(name="logistics_no")
    private String logisticsNo;
    //0表示未发货，1表示已发货，2表示已签收
    @Column
    private int status;
    @Column(name="group_partake_id")
    private String groupPartakeId;
    @Column(name = "refund_devivery_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime refundDeviveryTime;
    @Column(name = "refund_sign_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime refundSignTime;
    
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
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
    public String getLogisticsNo() {
        return logisticsNo;
    }
    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getGroupPartakeId() {
        return groupPartakeId;
    }
    public void setGroupPartakeId(String groupPartakeId) {
        this.groupPartakeId = groupPartakeId;
    }
    public DateTime getRefundDeviveryTime() {
        return refundDeviveryTime;
    }
    public void setRefundDeviveryTime(DateTime refundDeviveryTime) {
        this.refundDeviveryTime = refundDeviveryTime;
    }
    public DateTime getRefundSignTime() {
        return refundSignTime;
    }
    public void setRefundSignTime(DateTime refundSignTime) {
        this.refundSignTime = refundSignTime;
    }
    
}
