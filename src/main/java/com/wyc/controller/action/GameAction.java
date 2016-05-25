package com.wyc.controller.action;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.Good;
import com.wyc.domain.LuckDrawGood;
import com.wyc.domain.LuckDrawRecord;
import com.wyc.domain.LuckDrawUserInfo;
import com.wyc.domain.OpenGroupCoupon;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodService;
import com.wyc.service.LuckDrawGoodService;
import com.wyc.service.LuckDrawRecordService;
import com.wyc.service.LuckDrawUserInfoService;
import com.wyc.service.OpenGroupCouponService;
import com.wyc.wx.domain.UserInfo;

@Controller
public class GameAction {
    @Autowired
    private LuckDrawRecordService luckDrawRecordService;
    @Autowired
    private LuckDrawUserInfoService luckDrawUserInfoService;
    @Autowired
    private LuckDrawGoodService luckDrawGoodService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OpenGroupCouponService openGroupCouponService;
    @RequestMapping("/game/luck_draw")
    @UserInfoFromWebAnnotation
    public String luckDraw(HttpServletRequest httpServletRequest)throws Exception{
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        httpServletRequest.getSession().setAttribute(userInfo.getOpenid(), 11);
        Iterable<LuckDrawGood> luckDrawGoods = luckDrawGoodService.findAll();
        LuckDrawUserInfo luckDrawUserInfo = luckDrawUserInfoService.findByOpenid(userInfo.getOpenid());
        if(luckDrawUserInfo==null){
            luckDrawUserInfo = new LuckDrawUserInfo();
            luckDrawUserInfo.setOpenid(userInfo.getOpenid());
            luckDrawUserInfo = luckDrawUserInfoService.add(luckDrawUserInfo);
        }
        boolean isAllow = false;
        
        if(luckDrawUserInfo.getLastHandle()==null){
            isAllow = true;
        }else {
            Calendar nowDate = new GregorianCalendar();
            nowDate.setTime(new Date());
            Calendar lastHandleDate = new GregorianCalendar();
            lastHandleDate.setTime(luckDrawUserInfo.getLastHandle().toDate());
            if (nowDate.get(Calendar.YEAR) != lastHandleDate.get(Calendar.YEAR)
                    || nowDate.get(Calendar.MONTH) != lastHandleDate
                            .get(Calendar.MONTH)
                    || nowDate.get(Calendar.DAY_OF_MONTH) != lastHandleDate
                            .get(Calendar.DAY_OF_MONTH)) {
                isAllow = true;
            }
        }
        httpServletRequest.setAttribute("isAllow", isAllow);
        if(isAllow){
            String luckNo = UUID.randomUUID().toString();
            httpServletRequest.getSession().setAttribute(userInfo.getOpenid(), luckNo);
            httpServletRequest.setAttribute("luckNo", luckNo);
        }
        
        for(LuckDrawGood luckDrawGood:luckDrawGoods){
            if(luckDrawGood.getRecordIndex()==0){
                httpServletRequest.setAttribute("good0", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==1) {
                httpServletRequest.setAttribute("good1", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==2) {
                httpServletRequest.setAttribute("good2", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==3) {
                httpServletRequest.setAttribute("good3", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==4) {
                httpServletRequest.setAttribute("good4", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==5) {
                httpServletRequest.setAttribute("good5", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==6) {
                httpServletRequest.setAttribute("good6", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==7) {
                httpServletRequest.setAttribute("good7", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==8) {
                httpServletRequest.setAttribute("good8", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==9) {
                httpServletRequest.setAttribute("good9", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==10) {
                httpServletRequest.setAttribute("good10", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==11) {
                httpServletRequest.setAttribute("good11", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==12) {
                httpServletRequest.setAttribute("good12", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==13) {
                httpServletRequest.setAttribute("good13", luckDrawGood);
            }else if (luckDrawGood.getRecordIndex()==14) {
                httpServletRequest.setAttribute("good14", luckDrawGood);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String goods = objectMapper.writeValueAsString(luckDrawGoods);
            httpServletRequest.setAttribute("goods", goods);
        }
        return "game/luckDraw";
    }
    
    
    @UserInfoFromWebAnnotation
    @RequestMapping("/game/receive_good")
    public String receiveGood(HttpServletRequest httpServletRequest){
        String luckDrawRecordId = httpServletRequest.getParameter("luckDrawRecordId");
        LuckDrawRecord luckDrawRecord = luckDrawRecordService.findOne(luckDrawRecordId);
        
        OpenGroupCoupon openGroupCoupon = openGroupCouponService.findOne(luckDrawRecord.getTargetId());
        LuckDrawGood luckDrawGood = luckDrawGoodService.findOne(luckDrawRecord.getLuckDrawGoodId());
        Good good = goodService.findOne(luckDrawGood.getTargetId());
        httpServletRequest.setAttribute("goodName",good.getName());
        httpServletRequest.setAttribute("name", luckDrawGood.getName());
        httpServletRequest.setAttribute("imgUrl",luckDrawGood.getImgUrl());
        httpServletRequest.setAttribute("endDate", openGroupCoupon.getEndTime());
        httpServletRequest.setAttribute("couponId", openGroupCoupon.getId());
        httpServletRequest.setAttribute("goodId", good.getId());
        httpServletRequest.setAttribute("prompt", luckDrawGood.getPrompt());
        httpServletRequest.setAttribute("luckDrawRecordId", luckDrawRecord.getId());
        return "game/receiveGood";
    }
}
