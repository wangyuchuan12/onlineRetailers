package com.wyc.controller.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderAction {
    @RequestMapping("/main/order_list")
    public String orderList(){
        return "main/Orders";
    }
    
    @RequestMapping("/info/order_info")
    public String orderInfo(){
        return "info/OrderInfo";
    }
}
