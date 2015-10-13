package com.wyc.controller.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyc.annotation.JsApiTicketAnnotation;
import com.wyc.annotation.NowPageRecordAnnotation;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.annotation.WxConfigAnnotation;
import com.wyc.defineBean.MySimpleDateFormat;
import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.OrderDetail;
import com.wyc.domain.TemporaryData;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.MyResourceService;
import com.wyc.service.OrderDetailService;
import com.wyc.service.TemporaryDataService;
import com.wyc.service.WxUserInfoService;
import com.wyc.wx.domain.UserInfo;

@Controller
public class GroupsAction {
    @Autowired
    private GoodGroupService goodGroupService;
    @Autowired
    private GroupPartakeService groupPartakeService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private MyResourceService myResourceService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Autowired
    private MySimpleDateFormat mySimpleDateFormat;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private TemporaryDataService temporaryDataService;
    final static Logger logger = LoggerFactory.getLogger(GroupsAction.class);

    @RequestMapping("/main/group_list")
    @JsApiTicketAnnotation
	@UserInfoFromWebAnnotation
	@WxConfigAnnotation
	@NowPageRecordAnnotation(page=3)
    public String groupList(HttpServletRequest servletRequest) throws Exception {
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest) servletRequest;
        String openId = null;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        openId = userInfo.getOpenid();
        logger.debug("get openid {}", openId);
        if (openId == null || openId.trim().equals("")) {
            return "main/Groups";
        }
        Customer customer = customerService.findByOpenId(openId);
        logger.debug("get the customer is {}", customer);
        if (customer == null) {
            return "main/Groups";
        }
        String customerId = customer.getId();
        Iterable<GroupPartake> groupPartakes = groupPartakeService
                .findByCustomerid(customerId);
        List<String> groupIds = new ArrayList<String>();
        for (GroupPartake groupPartake : groupPartakes) {
            if(groupPartake.getGroupId()!=null){
                groupIds.add(groupPartake.getGroupId());
            }
        }
        logger.debug("current customer group id list is {}", groupIds);
        Iterable<GoodGroup> goodGroups = goodGroupService.findAll(groupIds);
        List<Map<String, String>> responseGroups = new ArrayList<Map<String, String>>();
        for (GoodGroup goodGroup : goodGroups) {
            Map<String, String> responseGroup = new HashMap<String, String>();
            if(goodGroup.getResult()==1){
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(goodGroup.getStartTime().toDate());
                calendar.add(Calendar.HOUR, goodGroup.getTimeLong());
                if(calendar.getTime().getTime()<new Date().getTime()){
                    goodGroup.setResult(0);
                    goodGroup = goodGroupService.save(goodGroup);
                }
            }
            
            responseGroup.put("result", goodGroup.getResult() + "");
            Good good = goodService.findOne(goodGroup.getGoodId());
            responseGroup.put("name", good.getName());
            responseGroup.put("head_img",
                    myResourceService.findOne(good.getHeadImg()).getUrl());
            responseGroup.put("total_price", goodGroup.getTotalPrice() + "");
            responseGroup.put("group_id", goodGroup.getId());
            responseGroups.add(responseGroup);
        }
        servletRequest.setAttribute("groups", responseGroups);
        return "main/Groups";
    }

    @RequestMapping("/info/takepart_group")
    @UserInfoFromWebAnnotation
    @Transactional
    public String takepartGroup(HttpServletRequest httpServletRequest)
            throws Exception {
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest) httpServletRequest;
        UserInfo requestUser = myHttpServletRequest.getUserInfo();
        Customer customer = customerService.findByOpenId(requestUser
                .getOpenid());
        String id = myHttpServletRequest.getParameter("id");
        String type = "2";
        GroupPartake groupPartake = groupPartakeService
                .findByCustomeridAndGroupId(customer.getId(), id);
        if (groupPartake != null) {
            return null;
        } else {
            groupPartake = new GroupPartake();
            groupPartake.setCustomerid(customer.getId());
            groupPartake.setDateTime(new DateTime());
            groupPartake.setGroupId(id);
            int count = groupPartakeService.countByGroupId(id);
            if (count == 0) {
                groupPartake.setRole(1);
            } else if (count == 1) {
                groupPartake.setRole(2);
            } else {
                groupPartake.setRole(3);
            }
            OrderDetail orderDetail = orderDetailService.findByGruopId(id);
            groupPartake.setType(Integer.parseInt(type));
            groupPartake.setOrderId(orderDetail.getOrderId());
            GoodGroup goodGroup = goodGroupService.findOne(id);
            int groupNum = goodGroup.getNum();
            logger.debug("take part group and the count is {} , groupNum is {}" , count , groupNum);
            if (groupNum == count) {
                return null;
            } else if (groupNum == count + 1) {
                goodGroup.setResult(2);
            }
            goodGroupService.save(goodGroup);
            groupPartakeService.add(groupPartake);
            return groupInfo(myHttpServletRequest);
        }

    }
    
    @UserInfoFromWebAnnotation
    @RequestMapping("/info/lastest_group_info")
    public String skipToLatestGroupInfo(HttpServletRequest httpServletRequest){
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        
        TemporaryData lastGroupId = temporaryDataService.findByMyKeyAndName(userInfo.getOpenid(),"lastGroupId");
        GoodGroup goodGroup = goodGroupService.findOne(lastGroupId.getValue());
        return "redirect:/info/group_info2?id="+goodGroup.getId()+"&token="+myHttpServletRequest.getToken().getId();
    }
    
    @RequestMapping("/info/group_info2")
    @JsApiTicketAnnotation
	@UserInfoFromWebAnnotation
	@WxConfigAnnotation
	@NowPageRecordAnnotation(page=4)
    public String groupInfo(HttpServletRequest httpServletRequest)
            throws Exception {
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest) httpServletRequest;
        UserInfo requestUser = myHttpServletRequest.getUserInfo();
        String id = httpServletRequest.getParameter("id");
        GoodGroup goodGroup = goodGroupService.findOne(id);
        logger.debug("the group id is {}", id);
        int result = goodGroup.getResult();
        Iterable<GroupPartake> groupPartakes = groupPartakeService
                .findAllByGroupIdOrderByRoleAsc(id);
        String goodId = goodGroup.getGoodId();
        Good good = goodService.findOne(goodId);
        String goodName = good.getName();
        String headImg = myResourceService.findOne(good.getHeadImg()).getUrl();
        int groupNum = goodGroup.getNum();
        float totalPrice = goodGroup.getTotalPrice();
        List<Map<String, String>> groupMembers = new ArrayList<Map<String, String>>();
        Integer role = 0;
        for (GroupPartake groupPartake : groupPartakes) {
            Map<String, String> groupMember = new HashMap<String, String>();
            String customerId = groupPartake.getCustomerid();
            Customer customer = customerService.findOne(customerId);
            String openid = customer.getOpenId();
            UserInfo userInfo = wxUserInfoService.findByOpenid(openid);
            groupMember.put("name", userInfo.getNickname());
            groupMember.put("headImg", userInfo.getHeadimgurl());
            groupMember.put("role", groupPartake.getRole() + "");
            groupMember.put("datetime", mySimpleDateFormat.format(groupPartake
                    .getDateTime().toDate()));
            groupMembers.add(groupMember);
            if (openid.equals(requestUser.getOpenid())) {
                role = groupPartake.getRole();
            }
        }

        Map<String, Object> groupInfoMap = new HashMap<String, Object>();
        groupInfoMap.put("result", result);
        groupInfoMap.put("goodName", goodName);
        groupInfoMap.put("headImg", headImg);
        groupInfoMap.put("id", id);
        groupInfoMap.put("goodId", good.getId());
        groupInfoMap.put("groupNum", groupNum);
        groupInfoMap.put("totalPrice", totalPrice);
        groupInfoMap.put("groupPartake", groupMembers);
        groupInfoMap.put("startTime",
                mySimpleDateFormat.format(goodGroup.getStartTime().toDate()));
        groupInfoMap.put("timeLong", goodGroup.getTimeLong());
        groupInfoMap.put("role", role);
        httpServletRequest.setAttribute("groupInfo", groupInfoMap);
        
        
        TemporaryData temporaryData = temporaryDataService.findByMyKeyAndName(requestUser.getOpenid(), "nowgroup");
        if(temporaryData==null){
            temporaryData = new TemporaryData();
            temporaryData.setMykey(requestUser.getOpenid());
            temporaryData.setName("nowgroup");
            temporaryData.setValue(id);
            temporaryDataService.add(temporaryData);
            
        }else{
            temporaryData.setValue(id);
            temporaryDataService.save(temporaryData);
        }
        return "info/GroupInfo";
    }

    @RequestMapping("/info/trade_flow_info")
    public String tradeFlowInfo(HttpServletRequest httpServletRequest)
            throws Exception {

        return "info/TradeFlowInfo";
    }
}
