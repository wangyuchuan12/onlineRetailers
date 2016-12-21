package com.wyc.domain.response.vo;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;


public class GoodViewVo implements Comparable<GoodViewVo>{

	
	private Object id;
	private Object instruction;
	private Object name;
    private Object alone_discount;
    private Object alone_original_cost;
    private Object flow_price;
    private Object group_discount;
    private Object group_num;
    private Object group_original_cost;
    private Object market_price;
    private Object coupon_cost;
    private Object title;
    private Object notice;
    private Object stock;
    private Object salesVolume;
    private Object adminId;
    private Object goodClickCount;
    
    private Object isGoodClick;
    
    private Object head_img;
    
    //0是商品 1是热团
    private Object type;
    
	private int seq;
	
	
	
	
	private Object group_id;
	private Object result;
    private Object total_price;
    private Object partake_num;
    
    private Object startTime;
    private Object timeLong;
	
	
	
	
	private List<Map<String, String>> members;
	
	
	
	
	
	
	public Object getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Object group_id) {
		this.group_id = group_id;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Object total_price) {
		this.total_price = total_price;
	}

	public Object getPartake_num() {
		return partake_num;
	}

	public void setPartake_num(Object partake_num) {
		this.partake_num = partake_num;
	}

	public Object getStartTime() {
		return startTime;
	}

	public void setStartTime(Object startTime) {
		this.startTime = startTime;
	}

	public Object getTimeLong() {
		return timeLong;
	}

	public void setTimeLong(Object timeLong) {
		this.timeLong = timeLong;
	}

	

	public List<Map<String, String>> getMembers() {
		return members;
	}

	public void setMembers(List<Map<String, String>> members) {
		this.members = members;
	}

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public Object getInstruction() {
		return instruction;
	}

	public void setInstruction(Object instruction) {
		this.instruction = instruction;
	}

	public Object getName() {
		return name;
	}

	public void setName(Object name) {
		this.name = name;
	}

	


	public Object getAlone_discount() {
		return alone_discount;
	}

	public void setAlone_discount(Object alone_discount) {
		this.alone_discount = alone_discount;
	}

	public Object getAlone_original_cost() {
		return alone_original_cost;
	}

	public void setAlone_original_cost(Object alone_original_cost) {
		this.alone_original_cost = alone_original_cost;
	}

	public Object getFlow_price() {
		return flow_price;
	}

	public void setFlow_price(Object flow_price) {
		this.flow_price = flow_price;
	}

	public Object getGroup_discount() {
		return group_discount;
	}

	public void setGroup_discount(Object group_discount) {
		this.group_discount = group_discount;
	}

	public Object getGroup_num() {
		return group_num;
	}

	public void setGroup_num(Object group_num) {
		this.group_num = group_num;
	}

	public Object getGroup_original_cost() {
		return group_original_cost;
	}

	public void setGroup_original_cost(Object group_original_cost) {
		this.group_original_cost = group_original_cost;
	}

	public Object getMarket_price() {
		return market_price;
	}

	public void setMarket_price(Object market_price) {
		this.market_price = market_price;
	}

	public Object getCoupon_cost() {
		return coupon_cost;
	}

	public void setCoupon_cost(Object coupon_cost) {
		this.coupon_cost = coupon_cost;
	}

	public Object getTitle() {
		return title;
	}

	public void setTitle(Object title) {
		this.title = title;
	}

	public Object getNotice() {
		return notice;
	}

	public void setNotice(Object notice) {
		this.notice = notice;
	}

	public Object getStock() {
		return stock;
	}

	public void setStock(Object stock) {
		this.stock = stock;
	}

	public Object getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Object salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Object getAdminId() {
		return adminId;
	}

	public void setAdminId(Object adminId) {
		this.adminId = adminId;
	}

	public Object getGoodClickCount() {
		return goodClickCount;
	}

	public void setGoodClickCount(Object goodClickCount) {
		this.goodClickCount = goodClickCount;
	}

	public Object getIsGoodClick() {
		return isGoodClick;
	}

	public void setIsGoodClick(Object isGoodClick) {
		this.isGoodClick = isGoodClick;
	}

	

	
	public Object getHead_img() {
		return head_img;
	}

	public void setHead_img(Object head_img) {
		this.head_img = head_img;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	@Override
	public int compareTo(GoodViewVo goodViewVo) {
		if(this.seq>goodViewVo.seq){
			return 1;
		}else if(this.seq<goodViewVo.seq){
			return -1;
		}else{
			return 0;
		}
	}  
}
