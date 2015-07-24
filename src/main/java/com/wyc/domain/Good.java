package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity(name = "good")
public class Good {
    @Id
    private String id;
    //组团打折
    @Column(name = "group_discount")
    private float groupDiscount;
    //单卖打折
    @Column(name = "alone_discount")
    private float aloneDiscount;
    //组团原来价格
    @Column(name = "group_original_cost")
    private float groupOriginalCost;
    //单卖原来价格
    @Column(name = "alone_original_cost")
    private float aloneOriginalCost;
    //市场价
    @Column(name = "market_price")
    private float marketPrice;
    //商品名称
    @Column(name = "name")
    private String name;
    //商品主题图片，这里指向了Resource表
    @Column(name = "head_img")
    private String headImg;
    //商品说明
    @Column
    private String instruction;
    @Column(name="group_num")
    private int groupNum;
    @Column(name="coupon_cost")
    private int couponCost=1;
    @Column(name="flow_price")
    private float flowPrice;
    @Column(name="source_id")
    private String sourceId;
    @Column(name="group_duration")
    private int groupDuration;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updateAt;
    public int getGroupDuration() {
        return groupDuration;
    }
    public void setGroupDuration(int groupDuration) {
        this.groupDuration = groupDuration;
    }
    public String getSourceId() {
        return sourceId;
    }
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
    public int getCouponCost() {
        return couponCost;
    }
    public void setCouponCost(int couponCost) {
        this.couponCost = couponCost;
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
    public float getMarketPrice() {
        return marketPrice;
    }
    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    public int getGroupNum() {
        return groupNum;
    }
    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }
    public float getGroupDiscount() {
        return groupDiscount;
    }
    public void setGroupDiscount(float groupDiscount) {
        this.groupDiscount = groupDiscount;
    }
    public float getAloneDiscount() {
        return aloneDiscount;
    }
    public void setAloneDiscount(float aloneDiscount) {
        this.aloneDiscount = aloneDiscount;
    }
    public float getGroupOriginalCost() {
        return groupOriginalCost;
    }
    public void setGroupOriginalCost(float groupOriginalCost) {
        this.groupOriginalCost = groupOriginalCost;
    }
    public float getAloneOriginalCost() {
        return aloneOriginalCost;
    }
    public void setAloneOriginalCost(float aloneOriginalCost) {
        this.aloneOriginalCost = aloneOriginalCost;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHeadImg() {
        return headImg;
    }
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
    public String getInstruction() {
        return instruction;
    }
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
