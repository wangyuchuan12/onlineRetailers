package com.wyc.domain;

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
    private float flowPrice;
    @Column
    private String code;
    //总共付款
    @Column(name = "cost")
    private float cost;
    @Column
    private String goodId;
    @Column
    private String address;
    @Column(name="address_id")
    private String addressId;
    //商品价格
    @Column(name = "good_price")
    private float goodPrice;
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

    public float getFlowPrice() {
        return flowPrice;
    }

    public void setFlowPrice(float flowPrice) {
        this.flowPrice = flowPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
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

    public float getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(float goodPrice) {
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
