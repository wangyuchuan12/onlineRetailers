package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

@Entity(name="system_pay_actity")
public class SystemPayActivity {
    @Id
    private String id;
    @Column
    private String handlers;
    @Column(name="good_id")
    @Index(name="good_id")
    private String goodId;
    @Column(name="user_open_ids")
    private String userOpenIds;
    @Column(name="pay_type")
    @Index(name="pay_type")
    private int payType;
    @Column(name="admin_id")
    private String adminId;
    public String getAdminId() {
        return adminId;
    }
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
   
    public String getHandlers() {
        return handlers;
    }
    public void setHandlers(String handlers) {
        this.handlers = handlers;
    }
    public String getGoodId() {
        return goodId;
    }
    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }
    public String getUserOpenIds() {
        return userOpenIds;
    }
    public void setUserOpenIds(String userOpenIds) {
        this.userOpenIds = userOpenIds;
    }
    public int getPayType() {
        return payType;
    }
    public void setPayType(int payType) {
        this.payType = payType;
    }
    
}
