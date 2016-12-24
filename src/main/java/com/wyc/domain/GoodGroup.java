package com.wyc.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
@Entity(name = "good_group")
public class GoodGroup {
    @Id
    private String id;
    //0表示组团失败1表示正在组团 2表示组团成功3表示组团超时
    @Column(name = "result")
    private int result;
    //团长 指向customer表
    @Column(name = "group_head_customer_id")
    private String groupHeadCustomerId;
    
    @Column(name = "group_head_group_partake_id")
    private String groupHeadGroupPartakeId;
    @Column(name="total_price")
    private BigDecimal totalPrice;
    //沙发
    @Column(name = "group_sofa")
    private String groupSofa;
    //开团时间
    @Column(name = "start_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startTime;
    
    private int num;
    //组团截止时长
    @Column(name = "time_long")
    private int timeLong;
    public int getTimeLong() {
        return timeLong;
    }
    public void setTimeLong(int timeLong) {
        this.timeLong = timeLong;
    }
    @Column(name="good_id")
    private String goodId;
    @Column(name="admin_id")
    private String adminId;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updateAt;
    @Column(name="is_disused")
    private int isDisused = 0;
    
    //是否统一收货 0否 1是
    @Column(name="is_receive_goods_together")
    private Integer isReceiveGoodsTogether;
    
    //统一收货人 指向了partake 的id
    @Column(name="together_receiver")
    private String togetherReceiver;
    @Column(name="good_price")
    private BigDecimal goodPrice;
    
    
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
    @Column(name="instead_relief",nullable=false)
    private BigDecimal receiverInsteadOfRelief;
    
    
    public BigDecimal getReceiverInsteadOfRelief() {
		return receiverInsteadOfRelief;
	}
	public void setReceiverInsteadOfRelief(BigDecimal receiverInsteadOfRelief) {
		this.receiverInsteadOfRelief = receiverInsteadOfRelief;
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
	public String getGroupHeadCustomerId() {
		return groupHeadCustomerId;
	}
	public void setGroupHeadCustomerId(String groupHeadCustomerId) {
		this.groupHeadCustomerId = groupHeadCustomerId;
	}
	public String getGroupHeadGroupPartakeId() {
		return groupHeadGroupPartakeId;
	}
	public void setGroupHeadGroupPartakeId(String groupHeadGroupPartakeId) {
		this.groupHeadGroupPartakeId = groupHeadGroupPartakeId;
	}
	public String getTogetherReceiver() {
		return togetherReceiver;
	}
	public void setTogetherReceiver(String togetherReceiver) {
		this.togetherReceiver = togetherReceiver;
	}
	public Integer getIsReceiveGoodsTogether() {
		return isReceiveGoodsTogether;
	}
	public void setIsReceiveGoodsTogether(Integer isReceiveGoodsTogether) {
		this.isReceiveGoodsTogether = isReceiveGoodsTogether;
	}
	public BigDecimal getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(BigDecimal goodPrice) {
		this.goodPrice = goodPrice;
	}
	public int getIsDisused() {
        return isDisused;
    }
    public void setIsDisused(int isDisused) {
        this.isDisused = isDisused;
    }
    public String getAdminId() {
        return adminId;
    }
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
    public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getGoodId() {
        return goodId;
    }
    public void setGoodId(String goodId) {
        this.goodId = goodId;
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
    public DateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getResult() {
        return result;
    }
    public void setResult(int result) {
        this.result = result;
    }
    public String getGroupSofa() {
        return groupSofa;
    }
    public void setGroupSofa(String groupSofa) {
        this.groupSofa = groupSofa;
    }
}
