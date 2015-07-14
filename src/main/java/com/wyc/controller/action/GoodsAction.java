package com.wyc.controller.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyc.domain.Good;
import com.wyc.domain.MyResource;
import com.wyc.service.GoodService;
import com.wyc.service.MyResourceService;
@Controller
public class GoodsAction {
        @Autowired
        private MyResourceService resourceService;
        @Autowired
        private GoodService goodService;
	@RequestMapping("/main/good_list")
	public String goodList(HttpServletRequest httpRequest){
	        Iterable<Good> databaseGoods = goodService.findAll();
		List<Map<String, Object>> responseGoods = new ArrayList<Map<String, Object>>();
		for(Good good:databaseGoods){
		    Map<String, Object> responseGood = new HashMap<String, Object>();
		    responseGood.put("id", good.getId());
		    responseGood.put("instruction", good.getInstruction());
		    responseGood.put("name", good.getName());
		    responseGood.put("alone_discount", good.getAloneDiscount());
		    responseGood.put("alone_original_cost", good.getAloneOriginalCost());
		    responseGood.put("flow_price", good.getFlowPrice());
		    responseGood.put("group_discount", good.getGroupDiscount());
		    responseGood.put("group_num", good.getGroupNum());
		    responseGood.put("group_original_cost", good.getGroupOriginalCost());
		    responseGood.put("market_price", good.getMarketPrice());
		    MyResource myResource = resourceService.findOne(good.getHeadImg());
		    if(myResource!=null){
		        responseGood.put("head_img", myResource.getUrl());
		    }
		    responseGoods.add(responseGood);
		}
		httpRequest.setAttribute("goods", responseGoods);
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
