package com.wyc.controller.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PersonalCenterAction {
    @RequestMapping("/main/personal_center")
    public String personCenter(HttpServletRequest httpServletRequest){
        return "main/PersonalCenter";
    }
    
    @RequestMapping("/info/address_add")
    public String addressAdd(HttpServletRequest httpServletRequest){
        return "info/AddressInfo";
    }
}
