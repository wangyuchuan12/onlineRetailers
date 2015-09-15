package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity(name = "order_detail")
public class OrderDetail {
    @Id
    private String id;
    @Column(name = "order_id",unique=true,nullable=false)
    private String orderId;
    @Column(name = "good_id",nullable=false)
    private String goodId;
    @Column(name = "num",nullable=false)
    private Integer num;
    @Column(name="group_id",unique = true)
    private String groupId;
    @Column(name="customer_id",nullable=false)
    private String customerId;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updateAt;
    //1表示未付款 2表示已付款 未发货 3表示已发货但未签收 4已签收 5退款未处理6退款已处理
    @Column(name = "status")
    private int status;
    @Column(name="out_trade_no",unique=true)
    private String outTradeNo;
    
    
    public String getOutTradeNo() {
        return outTradeNo;
    }
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getGoodId() {
        return goodId;
    }
    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }
    public Integer getNum() {
        return num;
    }
    public void setNum(Integer num) {
        this.num = num;
    }
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
