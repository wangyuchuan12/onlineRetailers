package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="luck_draw_record")
public class LuckDrawRecord {
    @Id
    private String id;
    @Column(name="handle_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime handleTime;
    @Column(name="luck_draw_good_id")
    private String luckDrawGoodId;
    @Column(name="luck_draw_user_info_id")
    private String luckDrawUserInfoId;
    @Column(name="target_id")
    private String targetId;
    //0未立即使用 1立即使用
    @Column
    private int status;
    
    @Column(name="invalid_date")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime invalidDate;
    
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;
    
    
    public String getTargetId() {
        return targetId;
    }
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
    public DateTime getInvalidDate() {
        return invalidDate;
    }
    public void setInvalidDate(DateTime invalidDate) {
        this.invalidDate = invalidDate;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public DateTime getHandleTime() {
        return handleTime;
    }
    public void setHandleTime(DateTime handleTime) {
        this.handleTime = handleTime;
    }
    public String getLuckDrawGoodId() {
        return luckDrawGoodId;
    }
    public void setLuckDrawGoodId(String luckDrawGoodId) {
        this.luckDrawGoodId = luckDrawGoodId;
    }
    public String getLuckDrawUserInfoId() {
        return luckDrawUserInfoId;
    }
    public void setLuckDrawUserInfoId(String luckDrawUserInfoId) {
        this.luckDrawUserInfoId = luckDrawUserInfoId;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
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
