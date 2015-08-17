package com.wyc.manager.controller.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class OrderManagerAction {
	@RequestMapping("/manager/orders")
    public String order(){
        return "manager/OrderManager";
    }
}
