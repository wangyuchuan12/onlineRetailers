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
    //团长
    @Column(name = "group_head")
    private String groupHead;
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
    
    @Column(name="relief_type")
    private Integer reliefType;
    
    @Column(name="relief_value")
    private BigDecimal reliefValue;
    
    @Column(name="good_price")
    private BigDecimal goodPrice;
    
    
    
    public BigDecimal getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(BigDecimal goodPrice) {
		this.goodPrice = goodPrice;
	}
	public BigDecimal getReliefValue() {
		return reliefValue;
	}
	public void setReliefValue(BigDecimal reliefValue) {
		this.reliefValue = reliefValue;
	}
	public Integer getReliefType() {
		return reliefType;
	}
	public void setReliefType(Integer reliefType) {
		this.reliefType = reliefType;
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
    public String getGroupHead() {
        return groupHead;
    }
    public void setGroupHead(String groupHead) {
        this.groupHead = groupHead;
    }
    public String getGroupSofa() {
        return groupSofa;
    }
    public void setGroupSofa(String groupSofa) {
        this.groupSofa = groupSofa;
    }
}
