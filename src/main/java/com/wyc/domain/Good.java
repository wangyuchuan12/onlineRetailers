package com.wyc.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "good")
public class Good {
    @Id
    private String id;
    //组团打折
    @Column(name = "group_discount")
    @JsonProperty(value="group_discount")
    private float groupDiscount;
    //单卖打折
    @Column(name = "alone_discount")
    @JsonProperty(value = "alone_discount")
    private float aloneDiscount;
    //组团原来价格
    @Column(name = "group_original_cost")
    @JsonProperty(value = "group_original_cost")
    private float groupOriginalCost;
    //单卖原来价格
    @Column(name = "alone_original_cost")
    @JsonProperty(value = "alone_original_cost")
    private float aloneOriginalCost;
    //市场价
    @Column(name = "market_price")
    @JsonProperty(value = "market_price")
    private float marketPrice;
    //商品名称
    @Column(name = "name")
    private String name;
    //商品主题图片，这里指向了Resource表
    @Column(name = "head_img")
    private String headImg;
    @Column(name="good_info_head_img")
    private String goodInfoHeadImg;
    //商品说明
    @Column(length=5000)
    private String instruction;
    @Column
    private String title;
    @Column(name="group_num")
    @JsonProperty(value = "group_num")
    private int groupNum;
    @Column(name="coupon_cost")
    @JsonProperty(value = "coupon_cost")
    private int couponCost=1;
    @Column(name="flow_price")
    @JsonProperty(value = "flow_price")
    private float flowPrice;
    @Column(name="source_id")
    @JsonProperty(value = "source_id")
    private String sourceId;
    @Column(name="group_duration")
    @JsonProperty(value = "group_duration")
    private int groupDuration;
    //0表示停用，1表示启用
    @Column
    private int status;
    @Column
    private int rank;
    @Column(name="time_long")
    private int timeLong = 24;
    
    @Column(name="good_type")
    private String goodType;
    @Column
    private String notice;
    @Column(name="is_display_main")
    private boolean isDisplayMain=false;
    @Column
    private Long stock=0L;
    @Column(name="admin_id")
    private String adminId;
    @Column(name="sales_volume")
    private Long salesVolume=0L;
    
    //允许代收货减免
    @Column(name="allow_instead_relief")
    private BigDecimal allowInsteadOfRelief;
    
    //强制代收货减免
    @Column(name="force_instead_relief")
    private BigDecimal forceInsteadOfRelief;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updateAt;
    
    
    
    
    public BigDecimal getAllowInsteadOfRelief() {
		return allowInsteadOfRelief;
	}
	public void setAllowInsteadOfRelief(BigDecimal allowInsteadOfRelief) {
		this.allowInsteadOfRelief = allowInsteadOfRelief;
	}


	public BigDecimal getForceInsteadOfRelief() {
		return forceInsteadOfRelief;
	}
	public void setForceInsteadOfRelief(BigDecimal forceInsteadOfRelief) {
		this.forceInsteadOfRelief = forceInsteadOfRelief;
	}
	public String getAdminId() {
        return adminId;
    }
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    public Long getStock() {
        return stock;
    }
    public void setStock(Long stock) {
        this.stock = stock;
    }
    public Long getSalesVolume() {
        return salesVolume;
    }
    public void setSalesVolume(Long salesVolume) {
        this.salesVolume = salesVolume;
    }
    public boolean isDisplayMain() {
        return isDisplayMain;
    }
    public void setDisplayMain(boolean isDisplayMain) {
        this.isDisplayMain = isDisplayMain;
    }
    public String getGoodInfoHeadImg() {
        return goodInfoHeadImg;
    }
    public void setGoodInfoHeadImg(String goodInfoHeadImg) {
        this.goodInfoHeadImg = goodInfoHeadImg;
    }
    public String getNotice() {
        return notice;
    }
    public void setNotice(String notice) {
        this.notice = notice;
    }
    public String getGoodType() {
        return goodType;
    }
    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }
    public int getTimeLong() {
        return timeLong;
    }
    public void setTimeLong(int timeLong) {
        this.timeLong = timeLong;
    }
    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
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
    
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
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
