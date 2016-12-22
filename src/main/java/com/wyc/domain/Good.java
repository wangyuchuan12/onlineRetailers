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
    private BigDecimal groupDiscount;
    //单卖打折
    @Column(name = "alone_discount")
    @JsonProperty(value = "alone_discount")
    private BigDecimal aloneDiscount;
    //组团原来价格
    @Column(name = "group_original_cost")
    @JsonProperty(value = "group_original_cost")
    private BigDecimal groupOriginalCost;
    //单卖原来价格
    @Column(name = "alone_original_cost")
    @JsonProperty(value = "alone_original_cost")
    private BigDecimal aloneOriginalCost;
    //市场价
    @Column(name = "market_price")
    @JsonProperty(value = "market_price")
    private BigDecimal marketPrice;
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
    private BigDecimal flowPrice;
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
    @Column(name="allow_instead_relief",nullable=false)
    private BigDecimal allowInsteadOfRelief;
    
    //强制代收货减免
    @Column(name="force_instead_relief",nullable=false)
    private BigDecimal forceInsteadOfRelief;
    
    //找人代收 本人减免
    @Column(name="instead_relief",nullable=false)
    private BigDecimal insteadOfRelief;
    
    //找人代收 代收人减免
   /* @Column(name="instead_relief",nullable=false)
    private BigDecimal receiverInsteadOfRelief;
    */
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updateAt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getGroupDiscount() {
		return groupDiscount;
	}
	public void setGroupDiscount(BigDecimal groupDiscount) {
		this.groupDiscount = groupDiscount;
	}
	public BigDecimal getAloneDiscount() {
		return aloneDiscount;
	}
	public void setAloneDiscount(BigDecimal aloneDiscount) {
		this.aloneDiscount = aloneDiscount;
	}
	public BigDecimal getGroupOriginalCost() {
		return groupOriginalCost;
	}
	public void setGroupOriginalCost(BigDecimal groupOriginalCost) {
		this.groupOriginalCost = groupOriginalCost;
	}
	public BigDecimal getAloneOriginalCost() {
		return aloneOriginalCost;
	}
	public void setAloneOriginalCost(BigDecimal aloneOriginalCost) {
		this.aloneOriginalCost = aloneOriginalCost;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
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
	public String getGoodInfoHeadImg() {
		return goodInfoHeadImg;
	}
	public void setGoodInfoHeadImg(String goodInfoHeadImg) {
		this.goodInfoHeadImg = goodInfoHeadImg;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}
	public int getCouponCost() {
		return couponCost;
	}
	public void setCouponCost(int couponCost) {
		this.couponCost = couponCost;
	}
	public BigDecimal getFlowPrice() {
		return flowPrice;
	}
	public void setFlowPrice(BigDecimal flowPrice) {
		this.flowPrice = flowPrice;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public int getGroupDuration() {
		return groupDuration;
	}
	public void setGroupDuration(int groupDuration) {
		this.groupDuration = groupDuration;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getTimeLong() {
		return timeLong;
	}
	public void setTimeLong(int timeLong) {
		this.timeLong = timeLong;
	}
	public String getGoodType() {
		return goodType;
	}
	public void setGoodType(String goodType) {
		this.goodType = goodType;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public boolean isDisplayMain() {
		return isDisplayMain;
	}
	public void setDisplayMain(boolean isDisplayMain) {
		this.isDisplayMain = isDisplayMain;
	}
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public Long getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(Long salesVolume) {
		this.salesVolume = salesVolume;
	}
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
	public BigDecimal getInsteadOfRelief() {
		return insteadOfRelief;
	}
	public void setInsteadOfRelief(BigDecimal insteadOfRelief) {
		this.insteadOfRelief = insteadOfRelief;
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
    
   
}
