package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="group_partake_log")
public class GroupPartakeLog {
	
	@Id
	private String id;
	
	//发生时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="happen_time")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime happenTime;
	
	@Column(name="content")
	private String content;
	//序号
	@Column
	private int seq;
	
	//团id
	@Column(name="group_Id")
	private String groupId;
	//参团人员id
	@Column(name="group_partake_id")
	private String groupPartakeId;
	
	@Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
	
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;

    
    
    
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DateTime getHappenTime() {
		return happenTime;
	}

	public void setHappenTime(DateTime happenTime) {
		this.happenTime = happenTime;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getGroupPartakeId() {
		return groupPartakeId;
	}

	public void setGroupPartakeId(String groupPartakeId) {
		this.groupPartakeId = groupPartakeId;
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
