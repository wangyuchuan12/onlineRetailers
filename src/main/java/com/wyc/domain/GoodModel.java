package com.wyc.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "good_model")
public class GoodModel {
	 @Id
	 private String id;
	 
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
    
    //商品详情商品图标
    @Column(name="good_info_head_img")
    private String goodInfoHeadImg;
    
    //商品说明
    @Column(length=5000)
    private String instruction;
    
    
    //标题
    @Column
    private String title;
    
    //提示信息
    @Column
    private String notice;
    
    
    //是否删除
    @Column(name="is_del")
    private int isDel;

    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
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

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}
    
    
    
}
