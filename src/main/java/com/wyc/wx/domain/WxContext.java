package com.wyc.wx.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name="wx_context")
public class WxContext {
    @Column
    private String appid;
    @Column
    private String appsecret;
    @Column(name="file_path")
    private String filePath;
    @Column
    private String flag;
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getAppid() {
        return appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public String getAppsecret() {
        return appsecret;
    }
    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
