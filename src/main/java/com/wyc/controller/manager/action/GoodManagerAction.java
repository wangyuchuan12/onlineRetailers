package com.wyc.controller.manager.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GoodManagerAction {
    @RequestMapping("/manager/good_add")
    public String managerGoodList(){
        return "manager/GoodAdd";
    }
}
