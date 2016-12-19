package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity(name="good_view")
@Table(indexes={
		@Index(columnList="good_type_id",name="good_type_id_index"),
		@Index(columnList="good_id",name="good_id_index"),
		@Index(columnList="hot_group_Id",name="hot_group_Id_index")})
public class GoodView {
	 @Id
	 private String id;
	 
	 //0是商品 1是热团
	 @Column
	 private int type;
	 
	 @Column(name="good_id")
	 private String goodId;
	 
	 
	 @Column(name="hot_group_Id")
	 private String hotGroupId;
	 
	 @Column(name="good_type_id")
	 private String goodTypeId;
	 
	 @Column
	 private int seq;

	 
	 
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

	public String getGoodTypeId() {
		return goodTypeId;
	}

	public void setGoodTypeId(String goodTypeId) {
		this.goodTypeId = goodTypeId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
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

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getHotGroupId() {
		return hotGroupId;
	}

	public void setHotGroupId(String hotGroupId) {
		this.hotGroupId = hotGroupId;
	}


	 
}
