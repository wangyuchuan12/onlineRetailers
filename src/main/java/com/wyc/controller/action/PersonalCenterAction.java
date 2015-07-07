package com.wyc.controller.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PersonalCenterAction {
    @RequestMapping("/main/personal_center")
    public String personCenter(){
        return "main/PersonalCenter";
    }
}
