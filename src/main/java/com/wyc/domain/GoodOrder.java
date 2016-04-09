package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
@Entity(name = "good_order")
public class GoodOrder {
    @Id
    private String id;
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
  //付款方式 0表示组团购买，1表示单买，2表示开团劵购买
    @Column
    private int type;
    @Column(name="group_id")
    private String groupId;
    //订单生成时间
    @Column(name = "create_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createTime;
    //发货时间
    @Column(name = "delivery_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime deliveryTime;
    //0表示购买失败，1表示购买成功，2表示购买进行中
    //1表示未付款 2表示已付款 未发货 3表示已发货但未签收 4已签收 5退款未处理6退款已处理
    @Column
    private int status;
    @Column(name="admin_id")
    
    
    private String adminId;
    
    
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //签收时间
    @Column(name = "sign_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime signTime;
    
    //付款时间
    @Column(name = "payment_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime paymentTime;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "update_at")
    private DateTime updateAt;
    
   
    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setFlowPrice(float flowPrice) {
        this.flowPrice = flowPrice;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(float goodPrice) {
        this.goodPrice = goodPrice;
    }


    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public DateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(DateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public DateTime getSignTime() {
        return signTime;
    }

    public void setSignTime(DateTime signTime) {
        this.signTime = signTime;
    }

    public DateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(DateTime paymentTime) {
        this.paymentTime = paymentTime;
    }
}
