package com.wyc.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
@Entity(name = "temp_group_order")
public class TempGroupOrder {
    @Id
    private String id;
    @Column(name="customer_address")
    private String customerAddress;
    @Column(name = "order_id")
    private String orderId;
    //付款方式 0表示组团购买，1表示单买，2表示开团劵购买 3参加组团
    @Column
    private int goodOrderType;
    //1团长 ， 2，沙发 3普通人
    private int role;
    //物流费用
    @Column(name = "flow_price")
    private BigDecimal flowPrice;
    @Column
    private String code;
    //总共付款
    @Column(name = "cost")
    private BigDecimal cost;
    @Column
    private String goodId;
    @Column
    private String address;
    @Column(name="address_id")
    private String addressId;
    //商品价格 是原价*折扣（不计算物流架构）
    @Column(name = "good_price")
    private BigDecimal goodPrice;
    @Column
    private Integer num;
    
    @Column(name="out_trade_no",unique=true)
    private String outTradeNo;
    @Column
    private String openid;
    
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updateAt;
    @Column(name="group_id")
    private String groupId;
    @Column(name="time_long")
    private int timeLong;
    @Column(name="admin_id")
    private String adminId;
    @Column(name="person_name")
    private String personName;
    @Column(name="phonenumber")
    private String phonenumber;
    @Column(name="good_style_id")
    private String goodStyleId;
    
    
    @Column(name="relief_value")
    private BigDecimal reliefValue;
    
    //是否允许代收0不允许 1允许
    @Column(name="is_instead_of_receiving")
    private int isInsteadOfReceiving;
    
    //是否脱他人代收 0不允许 1允许
    @Column(name="is_find_other_receiving")
    private int isFindOtherOfReceiving;
    
    //是否统一收货
    @Column(name="is_receive_goods_together")
    private int isReceiveGoodsTogether;
    
    //代收货参团者
    @Column(name="instead_partake_id")
    private String insteadPartakeId;
    
    //用户别呢
    @Column
    private String nickname;
    
    //用户头像
    @Column
    private String headImg;
    
    
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

	public BigDecimal getReceiverInsteadOfRelief() {
		return receiverInsteadOfRelief;
	}

	public void setReceiverInsteadOfRelief(BigDecimal receiverInsteadOfRelief) {
		this.receiverInsteadOfRelief = receiverInsteadOfRelief;
	}

	public int getIsReceiveGoodsTogether() {
		return isReceiveGoodsTogether;
	}

	public void setIsReceiveGoodsTogether(int isReceiveGoodsTogether) {
		this.isReceiveGoodsTogether = isReceiveGoodsTogether;
	}

	public int getIsFindOtherOfReceiving() {
		return isFindOtherOfReceiving;
	}

	public void setIsFindOtherOfReceiving(int isFindOtherOfReceiving) {
		this.isFindOtherOfReceiving = isFindOtherOfReceiving;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getInsteadPartakeId() {
		return insteadPartakeId;
	}

	public void setInsteadPartakeId(String insteadPartakeId) {
		this.insteadPartakeId = insteadPartakeId;
	}

	public int getIsInsteadOfReceiving() {
		return isInsteadOfReceiving;
	}

	public void setIsInsteadOfReceiving(int isInsteadOfReceiving) {
		this.isInsteadOfReceiving = isInsteadOfReceiving;
	}

	public BigDecimal getReliefValue() {
		return reliefValue;
	}

	public void setReliefValue(BigDecimal reliefValue) {
		this.reliefValue = reliefValue;
	}


	public String getGoodStyleId() {
        return goodStyleId;
    }

    public void setGoodStyleId(String goodStyleId) {
        this.goodStyleId = goodStyleId;
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

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public int getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(int timeLong) {
        this.timeLong = timeLong;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getGoodOrderType() {
        return goodOrderType;
    }

    public void setGoodOrderType(int goodOrderType) {
        this.goodOrderType = goodOrderType;
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public BigDecimal getFlowPrice() {
        return flowPrice;
    }

    public void setFlowPrice(BigDecimal flowPrice) {
        this.flowPrice = flowPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

   

    public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public BigDecimal getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(BigDecimal goodPrice) {
        this.goodPrice = goodPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}
