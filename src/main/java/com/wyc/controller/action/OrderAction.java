package com.wyc.controller.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderAction {
    @RequestMapping("/main/order_list")
    public String orderList(HttpServletRequest httpServletRequest){
        return "main/Orders";
    }
    
    @RequestMapping("/info/order_info")
    public String orderInfo(HttpServletRequest httpServletRequest){
        return "info/OrderInfo";
    }
}
