package com.wyc.controller.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GroupsAction {
    @RequestMapping("/main/group_list")
    public String groupList(){
        return "main/Groups";
    }
    @RequestMapping("/info/group_info")
    public String groupInfo(){
        return "info/GroupInfo";
    }
}
