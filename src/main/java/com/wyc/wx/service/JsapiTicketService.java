package com.wyc.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.util.Request;
import com.wyc.util.RequestFactory;
import com.wyc.util.Response;
import com.wyc.wx.domain.JsapiTicketBean;
@Service
public class JsapiTicketService {
    @Autowired
    private RequestFactory requestFactory;
    public JsapiTicketBean getJsapiTicketBean(String accessToken)throws Exception{
        Request request = requestFactory.jsapiTicketRequest(accessToken, "jsapi");
        Response response = request.get(null);
        JsapiTicketBean jsapiTicketBean = response.readObject(JsapiTicketBean.class);
        return jsapiTicketBean;
    }
}
