package com.wyc.annotation.handler;

import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
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

import com.wyc.domain.Good;
import com.wyc.domain.TemporaryData;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.GoodService;
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
    @Autowired
    private GoodService goodService;
    @Override
    public Object handle(HttpServletRequest httpServletRequest)throws Exception{
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        float cost = 0;
        System.out.println("........cost:"+httpServletRequest.getParameter("cost"));
        if(httpServletRequest.getAttribute("cost")!=null){
            cost = (Float)httpServletRequest.getAttribute("cost");
        }else{
            cost = Float.parseFloat(httpServletRequest.getParameter("cost"));
            httpServletRequest.setAttribute("cost", cost);
        }
        System.out.println("cost&&&&&&&&:"+cost);
        String goodId = httpServletRequest.getParameter("good_id");
        Good good = null;
        if(goodId!=null){
            good = goodService.findOne(goodId);
        }
        String payType=httpServletRequest.getParameter("pay_type");
        Calendar now = Calendar.getInstance();
        String outTradeNo = now.get(Calendar.YEAR)
                +"-"+(now.get(Calendar.MONTH) + 1)
                +"-"+now.get(Calendar.DAY_OF_MONTH)
                +"-"+now.get(Calendar.HOUR_OF_DAY)
                +"-"+now.get(Calendar.MINUTE)
                +"-"+now.get(Calendar.SECOND)
                +"-"+now.get(Calendar.MILLISECOND)
                +"-"+new Random().nextInt(1000)+"";
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        String openid = userInfo.getOpenid();
        if(!payType.equals("2")){
      
	        
	        
	        Request request = requestFactory.payUnifiedorder();
	        String appid = wxContext.getAppid();
	        String attach = "paytest";
	        String body = "晨曦商城";
	        String mchId = wxContext.getMchId();
	        String nonceStr = "1add1a30ac87aa2db72f57a2375d8f22";
	        String notifyUrl = "http://"+wxContext.getDomainName()+"/api/wx/pay_success";
	        String detail = "";
	        if(good!=null){
	            detail = good.getName();
	            body = good.getName();
	        }
	        String spbillCreateIp = httpServletRequest.getRemoteAddr();
	        String datetime = String.valueOf(System.currentTimeMillis() / 1000);
	        float totalFee = cost*100;
	        BigDecimal bigDecimal = new BigDecimal(totalFee);
	        String totalFeeStr = bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).longValue()+"";
	        
	        System.out.println("totalFeeStr...........:"+totalFeeStr);
	        String tradeType = "JSAPI";
	        TreeMap<String, String> map = new TreeMap<String, String>();
	        map.put("openid", openid);
	        map.put("body", body);
	        map.put("out_trade_no", outTradeNo);
	        map.put("total_fee", totalFeeStr);
	        map.put("notify_url", notifyUrl);
	        map.put("trade_type", tradeType);
	        map.put("appid", appid);
	        map.put("mch_id", mchId);
	        map.put("spbill_create_ip", spbillCreateIp);
	        map.put("nonce_str", nonceStr);
	        map.put("attach", attach);
	        map.put("detail", detail);
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
	        sb2.append("<detail>"+detail+"</detail>");
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
	        logger.debug("prepayId is {}",prepayId);
	        httpServletRequest.setAttribute("prepayId", prepayId);
	        httpServletRequest.setAttribute("pack", "prepay_id="+prepayId);
	        httpServletRequest.setAttribute("nonceStr", nonceStr);
	        httpServletRequest.setAttribute("paySign", paySign);
	        httpServletRequest.setAttribute("signType", "MD5");
	        httpServletRequest.setAttribute("timestamp", datetime);
	        
	        logger.debug("paySign is {}",paySign);
        }
        httpServletRequest.setAttribute("appId", wxContext.getAppid());
        
        httpServletRequest.setAttribute("outTradeNo",outTradeNo);
        
        TemporaryData goodIdTemporary = new TemporaryData();
        goodIdTemporary.setMykey(outTradeNo);
        goodIdTemporary.setKeyName("outTradeNo");
        goodIdTemporary.setName("goodId");
        goodIdTemporary.setValue(goodId);
        
        
        TemporaryData payTypeTemporary = new TemporaryData();
        payTypeTemporary.setMykey(outTradeNo);
        payTypeTemporary.setName("payType");
        payTypeTemporary.setKeyName("outTradeNo");
        payTypeTemporary.setValue(payType);
        
        temporaryDataService.add(goodIdTemporary);
        temporaryDataService.add(payTypeTemporary);
        
       
        
        TemporaryData openIdTemporary = new TemporaryData();
        openIdTemporary.setMykey(outTradeNo);
        openIdTemporary.setName("openId");
        openIdTemporary.setKeyName("outTradeNo");
        openIdTemporary.setValue(userInfo.getOpenid());
        temporaryDataService.add(openIdTemporary);
        
        TemporaryData userIdTemporary = new TemporaryData();
        userIdTemporary.setMykey(outTradeNo);
        userIdTemporary.setName("userId");
        userIdTemporary.setKeyName("outTradeNo");
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
