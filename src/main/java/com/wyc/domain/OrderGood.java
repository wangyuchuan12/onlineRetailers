package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//订单商品列表
@Entity(name = "order_good")
public class OrderGood {
    @Id
    private String id;
    @Column(name = "good_id")
    private String goodId;
    @Column(name = "num")
    private String num;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "order_price")
    private int orderPrice;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getGoodId() {
        return goodId;
    }
    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }
    public String getNum() {
        return num;
    }
    public void setNum(String num) {
        this.num = num;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public int getOrderPrice() {
        return orderPrice;
    }
    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }
}
