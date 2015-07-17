package com.wyc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
@Entity(name = "good_group")
public class GoodGroup {
    @Id
    private String id;
    @Column(name = "step")
    private int step;
    //1表示正在组团 2表示组团成功
    @Column(name = "result")
    private int result;
    //团长
    @Column(name = "group_head")
    private String groupHead;
    //沙发
    @Column(name = "group_sofa")
    private String groupSofa;
    //开团时间
    @Column(name = "start_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startTime;
    //组团截止时长
    @Column(name = "time_long")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private int timeLong;
    @Column(name="good_id")
    private int goodId;
    @Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createAt;
    @Column(name = "update_at")
    private DateTime updateAt;
    public int getGoodId() {
        return goodId;
    }
    public void setGoodId(int goodId) {
        this.goodId = goodId;
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
    public DateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }
    public int getTimeLong() {
        return timeLong;
    }
    public void setTimeLong(int timeLong) {
        this.timeLong = timeLong;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getStep() {
        return step;
    }
    public void setStep(int step) {
        this.step = step;
    }
    public int getResult() {
        return result;
    }
    public void setResult(int result) {
        this.result = result;
    }
    public String getGroupHead() {
        return groupHead;
    }
    public void setGroupHead(String groupHead) {
        this.groupHead = groupHead;
    }
    public String getGroupSofa() {
        return groupSofa;
    }
    public void setGroupSofa(String groupSofa) {
        this.groupSofa = groupSofa;
    }
}
