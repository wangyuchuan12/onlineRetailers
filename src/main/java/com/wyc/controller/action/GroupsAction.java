package com.wyc.controller.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GroupsAction {
    @RequestMapping("/main/group_list")
    public String groupList(HttpServletRequest servletRequest){
        Enumeration<String> attrs = servletRequest.getParameterNames();
        while((attrs.hasMoreElements())){
            System.out.println(attrs.nextElement());
        }
        return "main/Groups";
    }
    
    @RequestMapping("/info/group_info")
    public String groupInfo(){
        return "info/GroupInfo";
    }
    
    @RequestMapping("/info/trade_flow_info")
    public String tradeFlowInfo(){
        return "info/TradeFlowInfo";
    }
}
