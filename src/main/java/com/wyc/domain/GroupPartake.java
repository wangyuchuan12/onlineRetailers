package com.wyc.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
@Entity(name = "group_partake")
public class GroupPartake {
    public static final int WAIT_STATUS=0;
    public static final int BEGIN_STATUS=1;
    public static final int PROGRAM_STATUS=2;
    public static final int SUCCESS_STATUS=3;
    public static final int DERELICT_STATUS=4;
    public static final int FAILURE_STATUS=5;
    
    public static final int APPLY_STATUS=6;
   
    @Id
    private String id;
    @Column(name = "customerid")
    private String customerid;
    @Column
    private String openid;
    @Column(name = "group_id")
    private String groupId;
    @Column(name="customer_address")
    private String customerAddress;
    @Column(name="address_content")
    private String addressContent;
    @Column(name="person_name")
    private String personName;
    @Column
    private String phonenumber;
    @Column(name = "order_id")
    private String orderId;
    //付款方式 0表示组团购买，1表示单买，2表示开团劵购买
    @Column
    private int type;
    //1团长 ， 2，沙发 3普通人
    private int role;
    @Column
    private  String remarks;
    @Column
    private int status=WAIT_STATUS;
    @Column(name="is_del")
    private int isDel = 1;
    @Column(name="good_style_id")
    private String goodStyleId;
    @Column(name="good_style_name")
    private String goodStyleName;
    
    
    //是否允许代收
    @Column(name="is_instead_of_receiving")
    private int isInsteadOfReceiving;
    //参加时间
    @Column(name = "date_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime dateTime;
    
    //代收数量
    @Column(name="instead_num")
    private int insteadNum;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updateAt;
    
    //代收货参团者
    @Column(name="instead_partake_id")
    private String insteadPartakeId;
    
    //0自己接收 1允许他人代收 2全部统一收货
    @Column(name="relief_type")
    private Integer reliefType;
    
    //减免金额
    @Column(name="relief_value")
    private BigDecimal reliefValue;
    
    //微信别呢
    @Column
    private String nickname;
    
    //头像
    @Column
    private String headimgurl;
    
    
    
    
    public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Integer getReliefType() {
		return reliefType;
	}
	public void setReliefType(Integer reliefType) {
		this.reliefType = reliefType;
	}
	public BigDecimal getReliefValue() {
		return reliefValue;
	}
	public void setReliefValue(BigDecimal reliefValue) {
		this.reliefValue = reliefValue;
	}
	public String getInsteadPartakeId() {
		return insteadPartakeId;
	}
	public void setInsteadPartakeId(String insteadPartakeId) {
		this.insteadPartakeId = insteadPartakeId;
	}
	public int getInsteadNum() {
		return insteadNum;
	}
	public void setInsteadNum(int insteadNum) {
		this.insteadNum = insteadNum;
	}
	public int getIsInsteadOfReceiving() {
		return isInsteadOfReceiving;
	}
	public void setIsInsteadOfReceiving(int isInsteadOfReceiving) {
		this.isInsteadOfReceiving = isInsteadOfReceiving;
	}
	public String getGoodStyleName() {
        return goodStyleName;
    }
    public void setGoodStyleName(String goodStyleName) {
        this.goodStyleName = goodStyleName;
    }
    public String getGoodStyleId() {
        return goodStyleId;
    }
    public void setGoodStyleId(String goodStyleId) {
        this.goodStyleId = goodStyleId;
    }
    public int getIsDel() {
        return isDel;
    }
    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
    public String getAddressContent() {
        return addressContent;
    }
    public void setAddressContent(String addressContent) {
        this.addressContent = addressContent;
    }
    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getCustomerAddress() {
        return customerAddress;
    }
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
    }
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCustomerid() {
        return customerid;
    }
    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }
    public DateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
