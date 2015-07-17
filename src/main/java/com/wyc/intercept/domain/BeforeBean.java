package com.wyc.intercept.domain;

import com.wyc.wx.domain.UserInfo;

public class BeforeBean {
    private boolean enalble;
    private UserInfo userInfo;
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isEnalble() {
        return enalble;
    }

    public void setEnalble(boolean enalble) {
        this.enalble = enalble;
    }
}
