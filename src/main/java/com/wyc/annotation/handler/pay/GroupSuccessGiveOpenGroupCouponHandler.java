package com.wyc.annotation.handler.pay;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.GroupSuccessGiveOpenGroupCouponActivity;
import com.wyc.domain.OpenGroupCoupon;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.GroupSuccessGiveOpenGroupCouponActivityService;
import com.wyc.service.OpenGroupCouponService;

public class GroupSuccessGiveOpenGroupCouponHandler implements PayHandler{
    @Autowired
    private OpenGroupCouponService openGroupCouponService;
    @Autowired
    private GoodGroupService goodGroupService;
    @Autowired
    private GroupPartakeService groupPartakeService;
    @Autowired
    private GroupSuccessGiveOpenGroupCouponActivityService groupSuccessGiveOpenGroupCouponActivityService;
    final static Logger logger = LoggerFactory.getLogger(GroupSuccessGiveOpenGroupCouponHandler.class);
    @Override
    public void handler(String openId, String goodId, String groupId,
            String orderId, String outTradeNo, int payHandlerType,String activityId) {
        logger.debug("openId:"+openId+",goodId:"+goodId+",groupId:"+groupId+",orderId:"+orderId+",outTradeNo:"+outTradeNo+",payHandlerType:"+payHandlerType+",activityId:"+activityId);
        GoodGroup goodGroup = goodGroupService.findOne(groupId);
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
                    openGroupCoupon.setStatus(1);
                    openGroupCouponService.add(openGroupCoupon);
                }
            }
        }
    }
}
