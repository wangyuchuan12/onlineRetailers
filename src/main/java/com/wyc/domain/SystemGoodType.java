package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity(name = "system_good_type")
public class SystemGoodType {
    @Id
    private String id;
    @Column
    private String name;
    @Column(name="is_default")
    private boolean isDefault;
    @Column
    private String title;
    @Column
    private String img;
    @Column(name="is_display")
    private int isDisplay;
    @Column
    private String instruction;
    @Column(name="type_class")
    private String typeClass;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updateAt;
    
    
    
    public String getTypeClass() {
		return typeClass;
	}
	public void setTypeClass(String typeClass) {
		this.typeClass = typeClass;
	}
	public int getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(int isDisplay) {
		this.isDisplay = isDisplay;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public boolean isDefault() {
        return isDefault;
    }
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
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
