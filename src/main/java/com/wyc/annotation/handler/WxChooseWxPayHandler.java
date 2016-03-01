package com.wyc.annotation.handler;

import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.util.Calendar;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.domain.TemporaryData;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.TemporaryDataService;
import com.wyc.service.WxUserInfoService;
import com.wyc.util.MD5Util;
import com.wyc.util.Request;
import com.wyc.util.RequestFactory;
import com.wyc.util.Response;
import com.wyc.wx.domain.UserInfo;
import com.wyc.wx.domain.WxContext;

public class WxChooseWxPayHandler implements Handler{
    final static Logger logger = LoggerFactory.getLogger(WxChooseWxPayHandler.class);
    @Autowired
    private RequestFactory requestFactory;
    @Autowired
    private WxContext wxContext;
    @Autowired
    private TemporaryDataService temporaryDataService;
    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Override
    public Object handle(HttpServletRequest httpServletRequest)throws Exception{
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        float cost = 0;
        if(httpServletRequest.getAttribute("cost")!=null){
            cost = (Float)httpServletRequest.getAttribute("cost");
        }else{
            cost = Float.parseFloat(httpServletRequest.getParameter("cost"));
            httpServletRequest.setAttribute("cost", cost);
        }
        String goodId = httpServletRequest.getParameter("good_id");
        String payType=httpServletRequest.getParameter("pay_type");
        
      
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        String openid = userInfo.getOpenid();
        Request request = requestFactory.payUnifiedorder();
        String appid = wxContext.getAppid();
        String attach = "paytest";
        String body = "JSAPI";
        String mchId = wxContext.getMchId();
        String nonceStr = "1add1a30ac87aa2db72f57a2375d8fec";
        String notifyUrl = "http://www.chengxihome.com/api/wx/pay_success";
        Calendar now = Calendar.getInstance();
        String outTradeNo = now.get(Calendar.YEAR)
                +"-"+(now.get(Calendar.MONTH) + 1)
                +"-"+now.get(Calendar.DAY_OF_MONTH)
                +"-"+now.get(Calendar.HOUR_OF_DAY)
                +"-"+now.get(Calendar.MINUTE)
                +"-"+now.get(Calendar.SECOND)
                +"-"+new Random().nextInt(1000)+"";
        String spbillCreateIp = httpServletRequest.getRemoteAddr();
        String datetime = String.valueOf(System.currentTimeMillis() / 1000);
        Long totalFee = (long)(cost*100);
        String tradeType = "JSAPI";
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("openid", openid);
        map.put("body", body);
        map.put("out_trade_no", outTradeNo);
        map.put("total_fee", totalFee+"");
        map.put("notify_url", notifyUrl);
        map.put("trade_type", tradeType);
        map.put("appid", appid);
        map.put("mch_id", mchId);
        map.put("spbill_create_ip", spbillCreateIp);
        map.put("nonce_str", nonceStr);
        map.put("attach", attach);
        String sign = MD5Util.createMd5Sign(map,wxContext.getKey()).toUpperCase();
        StringBuffer sb2 = new StringBuffer();
        sb2.append("<xml>");
        sb2.append("<appid>"+appid+"</appid>");
        sb2.append("<attach>"+attach+"</attach>");
        sb2.append("<body>"+body+"</body>");
        sb2.append("<mch_id>"+mchId+"</mch_id>");
        sb2.append("<nonce_str>"+nonceStr+"</nonce_str>");
        sb2.append("<notify_url>"+notifyUrl+"</notify_url>");
        sb2.append("<openid>"+openid+"</openid>");
        sb2.append("<out_trade_no>"+outTradeNo+"</out_trade_no>");
        sb2.append("<spbill_create_ip>"+spbillCreateIp+"</spbill_create_ip>");
        sb2.append("<total_fee>"+totalFee+"</total_fee>");
        
        sb2.append("<trade_type>"+tradeType+"</trade_type>");
        sb2.append("<sign>"+sign+"</sign>");
        sb2.append("</xml>");
        logger.debug(sb2.toString());
        Response response = request.post(sb2.toString());
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(new StringReader(response.read()));
        Element rootElement = document.getRootElement();
        String prepayId = rootElement.getChildText("prepay_id");
        datetime = String.valueOf(System.currentTimeMillis() / 1000);
        SortedMap<String, String> map2  = new TreeMap<String, String>();
        map2.put("appId", wxContext.getAppid());
        map2.put("nonceStr", nonceStr);
        map2.put("package", "prepay_id="+prepayId);
        map2.put("signType", "MD5");
        map2.put("timeStamp", datetime);
        String paySign = MD5Util.createMd5Sign(map2,wxContext.getKey()).toUpperCase();
        logger.debug("paySign is {}",paySign);
        httpServletRequest.setAttribute("appId", wxContext.getAppid());
        httpServletRequest.setAttribute("prepayId", prepayId);
        httpServletRequest.setAttribute("pack", "prepay_id="+prepayId);
        httpServletRequest.setAttribute("nonceStr", nonceStr);
        httpServletRequest.setAttribute("paySign", paySign);
        httpServletRequest.setAttribute("signType", "MD5");
        httpServletRequest.setAttribute("timestamp", datetime);
        httpServletRequest.setAttribute("outTradeNo",outTradeNo);
        
        
        TemporaryData goodIdTemporary = new TemporaryData();
        goodIdTemporary.setMykey(outTradeNo);
        goodIdTemporary.setName("goodId");
        goodIdTemporary.setValue(goodId);
        
        
        TemporaryData payTypeTemporary = new TemporaryData();
        payTypeTemporary.setMykey(outTradeNo);
        payTypeTemporary.setName("payType");
        payTypeTemporary.setValue(payType);
        
        temporaryDataService.add(goodIdTemporary);
        temporaryDataService.add(payTypeTemporary);
        logger.debug("prepayId is {}",prepayId);
       
        
        TemporaryData openIdTemporary = new TemporaryData();
        openIdTemporary.setMykey(outTradeNo);
        openIdTemporary.setName("openId");
        openIdTemporary.setValue(userInfo.getOpenid());
        temporaryDataService.add(openIdTemporary);
        
        TemporaryData userIdTemporary = new TemporaryData();
        userIdTemporary.setMykey(outTradeNo);
        userIdTemporary.setName("userId");
        if(userInfo.getId()==null){
            userInfo = wxUserInfoService.findByOpenid(openid);
        }
        userIdTemporary.setValue(userInfo.getId());
        
        temporaryDataService.add(userIdTemporary);
        return null;
    }

    @Override
    public Class<? extends Handler>[] extendHandlers() {
        Class<? extends Handler>[] handlerClasses =  new Class[]{PayCostComputeHandler.class};
        return handlerClasses;
    }

    @Override
    public void setAnnotation(Annotation annotation) {
        // TODO Auto-generated method stub
        
    }
}
