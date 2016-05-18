package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="luck_draw_user_info")
public class LuckDrawUserInfo {
    @Id
    private String id;
    @Column(unique=true)
    private String openid;
    @Column(name="last_handle")
    private DateTime lastHandle;
    @Column
    private int count;
    @Column(name="last_luck_draw_record_id")
    private String lastLuckDrawRecordId;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;
    
    
    public String getLastLuckDrawRecordId() {
        return lastLuckDrawRecordId;
    }
    public void setLastLuckDrawRecordId(String lastLuckDrawRecordId) {
        this.lastLuckDrawRecordId = lastLuckDrawRecordId;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public DateTime getLastHandle() {
        return lastHandle;
    }
    public void setLastHandle(DateTime lastHandle) {
        this.lastHandle = lastHandle;
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
