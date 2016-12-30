package com.wyc.manage.domain.response;

import java.math.BigDecimal;

import com.wyc.annotation.IdAnnotation2;
import com.wyc.annotation.ParamAnnotation2;
import com.wyc.annotation.ParamEntityAnnotation2;


@ParamEntityAnnotation2
public class GoodModelResponse {
	@IdAnnotation2
	private String id;
	@ParamAnnotation2
    private String groupOriginalCost;
	@ParamAnnotation2
    private BigDecimal aloneOriginalCost;
	@ParamAnnotation2
    private BigDecimal marketPrice;
	@ParamAnnotation2
    private String name;
	@ParamAnnotation2
    private String headImg;
	@ParamAnnotation2
    private String goodInfoHeadImg;
	@ParamAnnotation2
    private String instruction;
	@ParamAnnotation2
    private String title;
	@ParamAnnotation2
    private String notice;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupOriginalCost() {
		return groupOriginalCost;
	}
	public void setGroupOriginalCost(String groupOriginalCost) {
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
