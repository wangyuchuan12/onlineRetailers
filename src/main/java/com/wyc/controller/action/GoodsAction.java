package com.wyc.controller.action;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class GoodsAction {
	public GoodsAction() {
		System.out.println(".....................GoodsAction");
	}
	@RequestMapping("/main/good_list")
	public String goodList(HttpServletRequest httpRequest){
		System.out.println(".............main/goods.jsp");
		return "main/Goods";
	}
	@RequestMapping("/info/good_info")
	public String goodInfo(){
	    System.out.println(".............main/goodInfo.jsp");
            return "info/GoodInfo";
	}
	@RequestMapping("/info/good_info_pay")
	public String gootInfoPay(){
	    return "info/GoodInfoPay";
	}
}
