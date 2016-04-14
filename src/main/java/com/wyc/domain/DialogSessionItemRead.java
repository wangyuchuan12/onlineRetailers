package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="dialog_session_item_read")
public class DialogSessionItemRead {
    public static final int CUSTOMER_ROLE = 0;
    public static final int ADMIN_ROLE=1;
    @Id
    private String id;
    @Column(name="item_id")
    private String itemId;
    @Column
    private int role;
    @Column(name="date_time")
    private DateTime dateTime;
    @Column(name="customer_id")
    private String customerId;
    @Column(name="admin_id")
    private String adminId;
    @Column
    private int count;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public int getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
    }
    public DateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getAdminId() {
        return adminId;
    }
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
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
}
