package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity(name = "city")
public class City {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "parent_id")
    private String parentId;
    //0表示省 1 表示市 2 表示地区
    @Column(name = "type")
    private int type;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updateAt;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
}
