package com.wyc.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.util.Request;
import com.wyc.util.RequestFactory;
import com.wyc.util.Response;
import com.wyc.wx.domain.AccessTokenBean;
import com.wyc.wx.domain.WxContext;

@Service
public class BasicSupportService {
    @Autowired
    private RequestFactory requestFactory;
    @Autowired
    private WxContext wxContext;
    public AccessTokenBean getAccessTokenBean()throws Exception{
        String appid = wxContext.getAppid();
        String secret = wxContext.getAppsecret();
        Request request = requestFactory.accessTokenRequest(appid, secret);
        Response response = request.get(null);
        AccessTokenBean accessTokenBean = response.readObject(AccessTokenBean.class);
        return accessTokenBean;
    }
}
