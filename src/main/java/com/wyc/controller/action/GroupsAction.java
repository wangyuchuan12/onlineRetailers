package com.wyc.controller.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wyc.annotation.AuthorizeAnnotation;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GroupPartake;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.MyResourceService;
import com.wyc.wx.domain.AccessTokenBean;
import com.wyc.wx.domain.Authorize;
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
    final static Logger logger = LoggerFactory.getLogger(GroupsAction.class);
    @RequestMapping("/main/group_list")
    @AuthorizeAnnotation
    public String groupList(HttpServletRequest servletRequest)throws Exception{
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest) servletRequest;
        String openId = null;
        Authorize authorize = myHttpServletRequest.getAuthorize();
        openId = authorize.getOpenid();
        logger.debug("get openid {}",openId);
        if(openId==null||openId.trim().equals("")){
            return "main/Groups";
        }
        Customer customer = customerService.findByOpenId(openId);
        logger.debug("get the customer is {}",customer);
        if(customer==null){
            return "main/Groups";
        }
        String customerId = customer.getId();
        Iterable<GroupPartake> groupPartakes = groupPartakeService.findByCustomerid(customerId);
        List<String> groupIds = new ArrayList<String>();
        for(GroupPartake groupPartake:groupPartakes){
            groupIds.add(groupPartake.getGroupId());
        }
        logger.debug("current customer group id list is {}",groupIds);
        Iterable<GoodGroup> goodGroups = goodGroupService.findAll(groupIds);
        List<Map<String, String>> responseGroups = new ArrayList<Map<String,String>>();
        for(GoodGroup goodGroup:goodGroups){
            Map<String, String> responseGroup = new HashMap<String, String>();
            responseGroup.put("step", goodGroup.getStep()+"");
            responseGroup.put("result", goodGroup.getResult()+"");
            Good good  = goodService.findOne(goodGroup.getGoodId());
            responseGroup.put("name", good.getName());
            responseGroup.put("head_img", myResourceService.findOne(good.getHeadImg()).getUrl());
            responseGroup.put("total_price", goodGroup.getTotalPrice()+"");
            responseGroup.put("group_id", goodGroup.getId());
            responseGroups.add(responseGroup);
        }
        servletRequest.setAttribute("groups", responseGroups);
        return "main/Groups";
    }
    
    @RequestMapping("/info/group_info2")
    @UserInfoFromWebAnnotation
    public String groupInfo(HttpServletRequest httpServletRequest)throws Exception{
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        String id = httpServletRequest.getParameter("id");
        GoodGroup goodGroup = goodGroupService.findOne(id);
        logger.debug("the group id is {}",id);
        int result = goodGroup.getResult();
        Iterable<GroupPartake> groupPartakes = groupPartakeService.findAllByGroupId(id);
        String goodId = goodGroup.getGoodId();
        Good good = goodService.findOne(goodId);
        String goodName = good.getName();
        String headImg = myResourceService.findOne(good.getHeadImg()).getUrl();
        int groupNum = good.getGroupNum();
        float totalPrice = goodGroup.getTotalPrice();
        List<Map<String, String>> groupMembers = new ArrayList<Map<String,String>>();
        for(GroupPartake groupPartake:groupPartakes){
            Map<String, String> groupMember = new HashMap<String, String>();
            String customerId = groupPartake.getCustomerid();
            Customer customer = customerService.findOne(customerId);
            String openid = customer.getOpenId();
            AccessTokenBean accessTokenBean = myHttpServletRequest.getAccessTokenBean();
            UserInfo userInfo = myHttpServletRequest.getUserInfo();
            groupMember.put("name", userInfo.getNickname());
            groupMember.put("headImg", userInfo.getHeadimgurl());
            groupMember.put("role", groupPartake.getRole()+"");
            SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            groupMember.put("datetime", sFormat.format(groupPartake.getDateTime().toDate()));
            groupMembers.add(groupMember);
        }
       
        Map<String, Object> groupInfoMap = new HashMap<String, Object>();
        groupInfoMap.put("result", result);
        groupInfoMap.put("goodName", goodName);
        groupInfoMap.put("headImg", headImg);
        groupInfoMap.put("groupNum", groupNum);
        groupInfoMap.put("totalPrice", totalPrice);
        groupInfoMap.put("groupPartake", groupMembers);
        httpServletRequest.setAttribute("groupInfo", groupInfoMap);
        return "info/GroupInfo";
    }
    @RequestMapping("/info/trade_flow_info")
    public String tradeFlowInfo(HttpServletRequest httpServletRequest)throws Exception{
        
        return "info/TradeFlowInfo";
    }
}
