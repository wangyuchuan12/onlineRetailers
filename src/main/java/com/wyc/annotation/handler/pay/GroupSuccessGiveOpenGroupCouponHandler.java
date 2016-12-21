package com.wyc.annotation.handler.pay;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodImg;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.GroupSuccessGiveOpenGroupCouponActivity;
import com.wyc.domain.NewsArticleItem;
import com.wyc.domain.OpenGroupCoupon;
import com.wyc.domain.PushArticle;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodImgService;
import com.wyc.service.GoodService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.GroupSuccessGiveOpenGroupCouponActivityService;
import com.wyc.service.MyResourceService;
import com.wyc.service.NewsArticleItemService;
import com.wyc.service.OpenGroupCouponService;
import com.wyc.service.PushArticleService;
import com.wyc.wx.domain.UserInfo;

public class GroupSuccessGiveOpenGroupCouponHandler implements PayHandler{
    @Autowired
    private OpenGroupCouponService openGroupCouponService;
    @Autowired
    private GoodGroupService goodGroupService;
    @Autowired
    private GroupPartakeService groupPartakeService;
    @Autowired
    private GroupSuccessGiveOpenGroupCouponActivityService groupSuccessGiveOpenGroupCouponActivityService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PushArticleService pushArticleService;
    @Autowired
    private NewsArticleItemService newsArticleItemService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private GoodImgService goodImgService;
    @Autowired
    private MyResourceService myResourceService;
    final static Logger logger = LoggerFactory.getLogger(GroupSuccessGiveOpenGroupCouponHandler.class);
    @Override
    public void handler(String openId, String goodId, String groupId,
            String orderId, String outTradeNo, int payHandlerType,String activityId) {
        logger.debug("openId:"+openId+",goodId:"+goodId+",groupId:"+groupId+",orderId:"+orderId+",outTradeNo:"+outTradeNo+",payHandlerType:"+payHandlerType+",activityId:"+activityId);
        GoodGroup goodGroup = goodGroupService.findOne(groupId);
        Good good = goodService.findOne(goodId);
        String headImgUrl = myResourceService.findOne(good.getHeadImg()).getUrl();
        int count = groupPartakeService.countByGroupId(groupId);
        if(goodGroup.getNum()==count){
            Iterable<GroupSuccessGiveOpenGroupCouponActivity> groupSuccessGivesOpenGroupCouponActivities = 
                    groupSuccessGiveOpenGroupCouponActivityService.findAllByActivityId(activityId);
            Iterable<GroupPartake> groupPartakes = groupPartakeService.findAllByGroupIdOrderByDateTime(goodGroup.getId());
            
            for(GroupPartake groupPartake:groupPartakes){
                for(GroupSuccessGiveOpenGroupCouponActivity groupSuccessGiveOpenGroupCouponActivity:groupSuccessGivesOpenGroupCouponActivities){
                    OpenGroupCoupon openGroupCoupon = new OpenGroupCoupon();
                    openGroupCoupon.setBeginTime(new DateTime());
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(new Date());
                    calendar.add(Calendar.HOUR,Integer.parseInt(groupSuccessGiveOpenGroupCouponActivity.getTimeLong()+""));
                    openGroupCoupon.setEndTime(new DateTime(calendar.getTime()));
                    openGroupCoupon.setCustomerId(groupPartake.getCustomerid());
                    openGroupCoupon.setCreateManager("system");
                    openGroupCoupon.setGoodId(groupSuccessGiveOpenGroupCouponActivity.getGoodId());
                    openGroupCoupon.setStatus(1);
                    openGroupCouponService.add(openGroupCoupon);
                    
                    Customer customer = customerService.findOne(groupPartake.getCustomerid());
                    PushArticle pushArticle = new PushArticle();
                    pushArticle.setFromUser("System:pay success");
                    pushArticle.setMsgtype(PushArticle.NEWS_TYPE);
                    pushArticle.setPushTime(new DateTime());
                    pushArticle.setStatus(PushArticle.NOT_SENT_STATUS);
                    pushArticle.setTouser(customer.getOpenId());
                    
                    pushArticle = pushArticleService.add(pushArticle);
                    
                    NewsArticleItem newsArticleItem = new NewsArticleItem();
                    newsArticleItem.setArticleId(pushArticle.getId());
                    newsArticleItem.setDescription("获得一张免费开团劵，用此卷可以免费购买开团价为"+
                    good.getGroupDiscount().multiply(good.getGroupOriginalCost())+"元的（"+good.getName()+"）商品");
                    newsArticleItem.setPicurl(headImgUrl);
                    newsArticleItem.setUrl("http://www.chengxihome.com/info/coupon");
                    newsArticleItem.setTitle("您收到一张团长免单劵");
                    newsArticleItem = newsArticleItemService.add(newsArticleItem);
                    
                }
            }
        }
    }
}
