package com.wyc.wx.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyc.annotation.IdAnnotation2;
import com.wyc.annotation.ParamAnnotation2;
import com.wyc.annotation.ParamEntityAnnotation2;


@ParamEntityAnnotation2(type=ParamEntityAnnotation2.SESSION_TYPE)
@Entity(name="user_info")
public class UserInfo {
    @Id
    @IdAnnotation2
    private String id;
    
    @ParamAnnotation2(type=ParamEntityAnnotation2.SESSION_TYPE)
    @Column(unique=true,updatable=false)
    private String openid;
    @Column
    @ParamAnnotation2
    private String nickname;
    @Column
    @ParamAnnotation2
    private String sex;
    @Column
    @ParamAnnotation2
    private String province;
    @Column
    @ParamAnnotation2
    private String city;
    @Column
    @ParamAnnotation2
    private String country;
    @Column
    @ParamAnnotation2
    private String headimgurl;
    @Column
    @ParamAnnotation2
    private String[] privilege;
    @Column
    @ParamAnnotation2
    private String unionid;
    @Column
    @ParamAnnotation2
    private String language;
    @Column
    @ParamAnnotation2
    private String remark;
    @Column
    @ParamAnnotation2
    private String subscribe_time;
    @Column
    @ParamAnnotation2
    private String groupid;
    @Column
    @ParamAnnotation2
    private String subscribe;
    @Column(unique=true)
    @ParamAnnotation2
    private String token;
    
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;
    @Column
    private Long count;
    
    
    
    public Long getCount() {
        return count;
    }
    public void setCount(Long count) {
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
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getSubscribe() {
        return subscribe;
    }
    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
   
    public String getSubscribe_time() {
        return subscribe_time;
    }
    public void setSubscribe_time(String subscribe_time) {
        this.subscribe_time = subscribe_time;
    }
    public String getGroupid() {
        return groupid;
    }
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getHeadimgurl() {
        return headimgurl;
    }
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
    public String[] getPrivilege() {
        return privilege;
    }
    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }
    public String getUnionid() {
        return unionid;
    }
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
