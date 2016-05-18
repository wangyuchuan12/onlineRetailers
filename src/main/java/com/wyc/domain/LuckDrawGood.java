package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="luck_draw_good")
public class LuckDrawGood {
    
    public static final int GROUP_COUPON_TYPE = 1;
    public static final int CASH_TYPE=2;
    @Id
    private String id;
    @Column(name="record_index")
    private int recordIndex;
    @Column
    private int type;
    @Column(name="target_id")
    private String targetId;
    @Column(name="img_url")
    private String imgUrl;
    @Column
    private String name;
    @Column
    private String prompt;
    @Column(name="is_allow")
    private boolean isAllow;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;

    public boolean isAllow() {
        return isAllow;
    }
    public void setAllow(boolean isAllow) {
        this.isAllow = isAllow;
    }
    public int getRecordIndex() {
        return recordIndex;
    }
    public void setRecordIndex(int recordIndex) {
        this.recordIndex = recordIndex;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getTargetId() {
        return targetId;
    }
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrompt() {
        return prompt;
    }
    public void setPrompt(String prompt) {
        this.prompt = prompt;
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
