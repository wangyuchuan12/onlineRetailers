package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
@Entity(name = "good_order")
public class GoodOrder {
    @Id
    private String id;
    //物流费用
    @Column(name = "flow_price")
    private float flowPrice;
    //总共付款
    @Column(name = "cost")
    private float cost;
    @Column
    private String goodId;
    //商品价格
    @Column(name = "good_price")
    private float goodPrice;
    //1表示未付款 2表示已付款 3表示未发货 4表示已发货但未签收 5已签收 6退款中 7退款成功
    @Column(name = "status")
    private int status;
    //订单生成时间
    @Column(name = "create_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createTime;
    //发货时间
    @Column(name = "delivery_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime deliveryTime;
    
    //签收时间
    @Column(name = "sign_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime signTime;
    
    //付款时间
    @Column(name = "payment_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime paymentTime;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "update_at")
    private DateTime updateAt;
    
   
    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public float getFlowPrice() {
        return flowPrice;
    }

    public void setFlowPrice(float flowPrice) {
        this.flowPrice = flowPrice;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(float goodPrice) {
        this.goodPrice = goodPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public DateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(DateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public DateTime getSignTime() {
        return signTime;
    }

    public void setSignTime(DateTime signTime) {
        this.signTime = signTime;
    }

    public DateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(DateTime paymentTime) {
        this.paymentTime = paymentTime;
    }
}
