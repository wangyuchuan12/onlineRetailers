package com.wyc.controller.api;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.LuckDrawGood;
import com.wyc.domain.LuckDrawRecord;
import com.wyc.domain.LuckDrawUserInfo;
import com.wyc.domain.MyResource;
import com.wyc.domain.NewsArticleItem;
import com.wyc.domain.OpenGroupCoupon;
import com.wyc.domain.PushArticle;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodService;
import com.wyc.service.LuckDrawGoodService;
import com.wyc.service.LuckDrawRecordService;
import com.wyc.service.LuckDrawUserInfoService;
import com.wyc.service.MyResourceService;
import com.wyc.service.NewsArticleItemService;
import com.wyc.service.OpenGroupCouponService;
import com.wyc.service.PushArticleService;
import com.wyc.wx.domain.UserInfo;

@RestController
public class GameApi {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private LuckDrawGoodService luckDrawGoodService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private OpenGroupCouponService openGroupCouponService;
    @Autowired
    private LuckDrawUserInfoService luckDrawUserInfoService;
    @Autowired
    private LuckDrawRecordService luckDrawRecordService;
    @Autowired
    private PushArticleService pushArticleService;
    @Autowired
    private NewsArticleItemService newsArticleItemService;
    @Autowired
    private MyResourceService myResourceService;
    @UserInfoFromWebAnnotation
    @RequestMapping("/game/draw_handler")
    public Object drawHandler(HttpServletRequest httpServletRequest){
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        String luckNo1 = httpServletRequest.getParameter("luckNo");
        String luckNo2 = httpServletRequest.getSession().getAttribute(userInfo.getOpenid()).toString();
        if(!luckNo1.equals(luckNo2)){
            throw new RuntimeException("没有预设luckNo");
        }else{
            Customer customer = customerService.findByOpenId(userInfo.getOpenid());
            String index = httpServletRequest.getParameter("index");
            LuckDrawGood luckDrawGood = luckDrawGoodService.findByRecordIndex(Integer.parseInt(index));
            Good good = goodService.findOne(luckDrawGood.getTargetId());
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, 24);
            OpenGroupCoupon openGroupCoupon = new OpenGroupCoupon();
            openGroupCoupon.setBeginTime(new DateTime());
            openGroupCoupon.setEndTime(new DateTime(calendar.getTime()));
            openGroupCoupon.setCreateManager("system_game");
            openGroupCoupon.setCustomerId(customer.getId());
            openGroupCoupon.setGoodId(good.getId());
            openGroupCoupon.setStatus(1);
            openGroupCoupon = openGroupCouponService.add(openGroupCoupon);
            Map<String, Object> map = new HashMap<String, Object>();
            LuckDrawUserInfo luckDrawUserInfo = luckDrawUserInfoService.findByOpenid(userInfo.getOpenid());
            
            LuckDrawRecord luckDrawRecord = new LuckDrawRecord();
            luckDrawRecord.setInvalidDate(new DateTime(calendar.getTime()));
            luckDrawRecord.setLuckDrawGoodId(luckDrawGood.getId());
            luckDrawRecord.setLuckDrawUserInfoId(luckDrawUserInfo.getId());
            luckDrawRecord.setHandleTime(new DateTime());
            luckDrawRecord.setStatus(0);
            luckDrawRecord.setTargetId(openGroupCoupon.getId());
            luckDrawRecord = luckDrawRecordService.add(luckDrawRecord);
            
            
            luckDrawUserInfo.setLastHandle(new DateTime());
            luckDrawUserInfo.setCount(luckDrawUserInfo.getCount()+1);
            luckDrawUserInfo.setLastLuckDrawRecordId(luckDrawRecord.getId());
            luckDrawUserInfo = luckDrawUserInfoService.save(luckDrawUserInfo);
            map.put("goodName",good.getName());
            map.put("endDate", openGroupCoupon.getEndTime());
            map.put("couponId", openGroupCoupon.getId());
            map.put("goodId", good.getId());
            map.put("luckNo", luckNo1);
            map.put("prompt", luckDrawGood.getPrompt());
            map.put("luckDrawUserInfoId", luckDrawUserInfo.getId());
            map.put("luckDrawRecordId", luckDrawRecord.getId());
            map.put("type", luckDrawGood.getType());
            
            
            
            PushArticle pushArticle = new PushArticle();
            pushArticle.setFromUser("System:games");
            pushArticle.setMsgtype(PushArticle.NEWS_TYPE);
            pushArticle.setPushTime(new DateTime());
            pushArticle.setStatus(PushArticle.NOT_SENT_STATUS);
            pushArticle.setTouser(customer.getOpenId());
            
            pushArticle = pushArticleService.add(pushArticle);
            
            NewsArticleItem newsArticleItem = new NewsArticleItem();
            newsArticleItem.setArticleId(pushArticle.getId());
            newsArticleItem.setDescription("获得一张免费开团劵，用此卷可以免费购买开团价为"+
            good.getGroupDiscount().multiply(good.getGroupOriginalCost())+"元的（"+good.getName()+"）商品");
            MyResource myResource = myResourceService.findOne(good.getHeadImg());
            newsArticleItem.setPicurl(myResource.getUrl());
            newsArticleItem.setUrl("http://www.chengxihome.com/info/coupon");
            newsArticleItem.setTitle("您收到一张团长免单劵");
            newsArticleItem = newsArticleItemService.add(newsArticleItem);
            return map;
        }  
    }
}
