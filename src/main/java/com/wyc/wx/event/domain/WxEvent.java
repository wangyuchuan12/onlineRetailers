package com.wyc.wx.event.domain;

import javax.persistence.Column;

public class WxEvent {
    @Column(name="ToUserName")
    private String toUserName;
    @Column(name="FromUserName")
    private String fromUserName;
    @Column(name="CreateTime")
    private String createTime;
    @Column(name="MsgType")
    private String msgType;
    @Column(name="Event")
    private String event;
    public String getToUserName() {
        return toUserName;
    }
    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
    public String getFromUserName() {
        return fromUserName;
    }
    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getMsgType() {
        return msgType;
    }
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
    public String getEvent() {
        return event;
    }
    public void setEvent(String event) {
        this.event = event;
    }
    
}
