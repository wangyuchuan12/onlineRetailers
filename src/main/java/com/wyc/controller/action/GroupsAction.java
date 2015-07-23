package com.wyc.controller.action;

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

import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GroupPartake;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.MyResourceService;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.service.OauthService;
import com.wyc.wx.service.UserService;

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
    private UserService userService;
    @Autowired
    private OauthService oauthService;
    @Autowired
    private CustomerService customerService;
    final static Logger logger = LoggerFactory.getLogger(GroupsAction.class);
    @RequestMapping("/main/group_list")
    public String groupList(HttpServletRequest servletRequest){
        String code = servletRequest.getParameter("code");
        logger.debug("the code is:{}",code);
        String openId = null;
        try {
            Authorize authorize = oauthService.getAuthorizeByCode(code);
            openId = authorize.getOpenid();
            logger.debug("get openid {}",openId);
            if(openId==null||openId.trim().equals("")){
                return "main/Groups";
            }
        } catch (Exception e) {
            logger.error("get openid error,the code is {}",code);
        }
        Customer customer = customerService.findByOpenId(openId);
        if(customer==null){
            return "main/Groups";
        }
        String customerId = customer.getId();
        Iterable<GroupPartake> groupPartakes = groupPartakeService.findByCustomerid(customerId);
        List<String> groupIds = new ArrayList<String>();
        for(GroupPartake groupPartake:groupPartakes){
            groupIds.add(groupPartake.getOrderId());
        }
       
        Iterable<GoodGroup> goodGroups = goodGroupService.findAll(groupIds);
        List<Map<String, String>> responseGroups = new ArrayList<Map<String,String>>();
        for(GoodGroup goodGroup:goodGroups){
            Map<String, String> responseGroup = new HashMap<String, String>();
            responseGroup.put("step", goodGroup.getStep()+"");
            responseGroup.put("result", goodGroup.getResult()+"");
            Good good  = goodService.findOne(goodGroup.getGoodId());
            if(good!=null){
                responseGroup.put("name", good.getName());
                responseGroup.put("head_img", myResourceService.findOne(good.getHeadImg()).getUrl());
                responseGroup.put("total_price", goodGroup.getTotalPrice()+"");
            }
            responseGroups.add(responseGroup);
        }
        
        servletRequest.setAttribute("groups", responseGroups);
        return "main/Groups";
    }
    
    @RequestMapping("/info/group_info")
    public String groupInfo(HttpServletRequest httpServletRequest){
        
        String id = httpServletRequest.getParameter("id");
        GoodGroup goodGroup = goodGroupService.findOne(id);
        int result = goodGroup.getResult();
        Iterable<GroupPartake> groupPartakes = groupPartakeService.findAllByGroupId(id);
        String goodId = goodGroup.getGoodId();
        Good good = goodService.findOne(goodId);
        String goodName = good.getName();
        String headImg = myResourceService.findOne(good.getHeadImg()).getUrl();
        
        return "info/GroupInfo";
    }
    
    @RequestMapping("/info/trade_flow_info")
    public String tradeFlowInfo(){
        return "info/TradeFlowInfo";
    }
}
