package com.wyc.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.Good;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.OrderDetail;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.OrderDetailService;
import com.wyc.wx.domain.UserInfo;

@RestController
public class GoodsApi {
	@Autowired
	private GoodService goodService;
	@Autowired
	private GoodOrderService goodOrderService;
	@Autowired
	private GoodGroupService goodGroupService;
	@Autowired
	private OrderDetailService orderDetailService;
	@RequestMapping(value="/api/pay_success")
	@UserInfoFromWebAnnotation
	public Object paySouccess(HttpServletRequest httpServletRequest){
		MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
		UserInfo userInfo = myHttpServletRequest.getUserInfo();
		String good_id = httpServletRequest.getParameter("good_id");
		String payType = httpServletRequest.getParameter("pay_type");
		String status = httpServletRequest.getParameter("status");
		if(status.equals("1")||status.equals("2")){
			Good good = goodService.findOne(good_id);
			float cost = 0 ;
			if(payType.equals("0")){
	            cost = good.getFlowPrice()+good.getGroupDiscount()*good.getGroupOriginalCost();
	        }else if (payType.equals("1")) {
	            cost = good.getFlowPrice()+good.getAloneDiscount()*good.getAloneOriginalCost();
	        }else if (payType.equals("2")) {
	            cost = good.getFlowPrice();
	        }
			GoodOrder goodOrder = new GoodOrder();
			goodOrder.setGoodId(good.getId());
			goodOrder.setGoodPrice(good.getFlowPrice());
			goodOrder.setCost(cost);
			goodOrder.setCreateTime(new DateTime());
			goodOrder.setFlowPrice(good.getFlowPrice());
			goodOrder.setStatus(Integer.parseInt(status));
			goodOrder = goodOrderService.add(goodOrder);
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setGoodId(good.getId());
			orderDetail.setNum(good.getGroupNum());
			orderDetail.setOrderId(goodOrder.getId());
			if(status.equals("2")){
				GoodGroup goodGroup = new GoodGroup();
				goodGroup.setGoodId(good.getId());
				goodGroup.setGroupHead(userInfo.getId());
				goodGroup.setResult(1);
				goodGroup.setStartTime(new DateTime());
				goodGroup.setTimeLong(24);
				goodGroup.setTotalPrice(cost);
				goodGroup = goodGroupService.add(goodGroup);
				orderDetail.setGroupId(goodGroup.getGoodId());
				
			}
		}
		return null;
	}
}
