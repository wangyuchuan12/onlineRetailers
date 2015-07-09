package com.wyc.test;


import org.apache.commons.codec.digest.DigestUtils;

import com.wyc.util.Request;
import com.wyc.util.RequestFactory;
import com.wyc.util.Response;
import com.wyc.wx.domain.AccessTokenBean;
import com.wyc.wx.domain.JsapiTicketBean;

public class TestMain {
    public static void main(String[]args)throws Exception{
        System.out.println("sdf");
        System.out.println(System.currentTimeMillis());
        getTicket();
        jiami();
       
    }
    
    public static void jiami()throws Exception{

        String mima = DigestUtils.sha1Hex("jsapi_ticket=kgt8ON7yVITDhtdwci0qeS9Tjc5DK9ogBGC5AD_PDIilsjN1T_2-E0ldsyjrh4PINyYC34bYqEr3iwUh-OFJ7w&noncestr=wangyuchuan12&timestamp=1436266726478&url=http://121.43.104.22/testjquery/Orders.html");
        System.out.println(mima);
        //  System.out.println(DigestUtils.shaHex("jsapi_ticket=IpK_1T69hDhZkLQTlwsAX-XuRgbw3AnW65uUKmjeE1ROhm3se1Vo1uzkbqxZRB9kqWbIS1Y3h1oNPpLz3aoF9g&noncestr=wangyuchuan&timestamp=1436266726478&url=http://121.43.104.22/testjquery/Orders.html"));
        
    }
    public static String getTicket()throws Exception{
        String appID = "wx7e3ed2dc655c0145";
        String appsecret = "122630a26c1e0c6b706ec7659e33f752";
        RequestFactory requestFactory = new RequestFactory();
        Request request = requestFactory.accessTokenRequest(appID, appsecret);
        Response response = request.get(null);
        AccessTokenBean accessTokenBean = response.readObject(AccessTokenBean.class);
        Request jsapiTicketRequest = requestFactory.jsapiTicketRequest(accessTokenBean.getAccess_token(), "jsapi");
        Response jsapiTicketResponse = jsapiTicketRequest.get(null);
        JsapiTicketBean jsapiTicketBean = jsapiTicketResponse.readObject(JsapiTicketBean.class);
        System.out.println(jsapiTicketBean.getTicket());
        return jsapiTicketBean.getTicket();
    }
}
