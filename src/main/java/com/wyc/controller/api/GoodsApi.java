package com.wyc.controller.api;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.annotation.AfterHandlerAnnotation;
import com.wyc.annotation.ResponseJson;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.annotation.handler.PayResultHandler;
import com.wyc.domain.GoodClickGood;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.GoodClickGoodService;
import com.wyc.wx.domain.UserInfo;

@RestController
public class GoodsApi {
	@Autowired
    private GoodClickGoodService goodClickGoodService;
    @RequestMapping(value = "/api/pay_failure")
//    @UserInfoFromWebAnnotation
//    @AfterHandlerAnnotation(hanlerClasses={PayResultHandler.class})
//    @ResponseJson(names={"orderId"})
    public Object payFailure(HttpServletRequest httpServletRequest)throws Exception{
//        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
//        UserInfo userInfo = myHttpServletRequest.getUserInfo();
//        String goodId = httpServletRequest.getParameter("good_id");
//        String payType = httpServletRequest.getParameter("pay_type");
//     //   String status = httpServletRequest.getParameter("status");
//        
//        httpServletRequest.setAttribute("good_id", goodId);
//        httpServletRequest.setAttribute("pay_type", payType);
//        httpServletRequest.setAttribute("status", 1);
//        httpServletRequest.setAttribute("openId", userInfo.getOpenid());
//        httpServletRequest.setAttribute("userId", userInfo.getId());
        return null;

    }
    @UserInfoFromWebAnnotation
    @RequestMapping(value = "/api/click_good")
    public Object clickGood(HttpServletRequest httpServletRequest){
    	String goodId = httpServletRequest.getParameter("good_id");
    	 MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
    	 UserInfo userInfo = myHttpServletRequest.getUserInfo();
    	 String openId = userInfo.getOpenid();
    	 GoodClickGood goodClickGood = goodClickGoodService.findByOpenidAndGoodId(openId,goodId);
    	 if(goodClickGood==null){
    		 goodClickGood = new GoodClickGood();
    		 goodClickGood.setDateTime(new DateTime());
    		 goodClickGood.setGoodId(goodId);
    		 goodClickGood.setOpenid(openId);
    		 goodClickGoodService.add(goodClickGood);
    	 }
    	 Map<String, Object> responseMap = new HashMap<>();
    	 responseMap.put("success", true);
    	 return responseMap;
    }
}
