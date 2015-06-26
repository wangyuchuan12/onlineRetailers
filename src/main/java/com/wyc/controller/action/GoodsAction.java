package com.wyc.controller.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class GoodsAction {
	public GoodsAction() {
		System.out.println(".....................GoodsAction");
	}
	@RequestMapping("/main/good_list")
	public String goodList(){
		System.out.println(".............main/goods.jsp");
		return "main/Goods";
	}
	@RequestMapping("/info/good_info")
	public String goodInfo(){
	    System.out.println(".............main/goodInfo.jsp");
            return "info/GoodInfo";
	}
}
