package com.wyc.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity(name = "group_partake_payment")
public class GroupPartakePayment {
    @Id
    private String id;
    //总共付款
    @Column(name = "cost")
    private BigDecimal cost;
    
    //退款金额
    @Column(name="refund_amount")
    private float refundAmount;
    
    //退款时间
    @Column(name = "refund_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime refundTime;
    
    //付款时间
    @Column(name="pay_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime payTime;
    
    //0表示未付款 1已付款 2申请退款 3退款完成 4退款拒绝
    @Column
    private int status;
    
    //是否允许申请退款 0不允许 1允许
    @Column(name="is_allow_refund")
    private int isAllowRefund=0;
    
    //是否允许申请退款拒绝 0不允许 1允许
    @Column(name="is_allow_refuse_refund")
    private int isAllowRefuseRefund=0;
    @Column(name="out_trade_no")
    private String outTradeNo;
    @Column(name="group_partake_id")
    private String groupPartakeId;

    
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;
    
    
    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public float getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(float refundAmount) {
        this.refundAmount = refundAmount;
    }

    public DateTime getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(DateTime refundTime) {
        this.refundTime = refundTime;
    }

    public DateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(DateTime payTime) {
        this.payTime = payTime;
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
    
    
}
