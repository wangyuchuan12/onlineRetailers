package com.wyc.controller.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.OrderDetail;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.OrderDetailService;
import com.wyc.service.WxUserInfoService;
import com.wyc.wx.domain.UserInfo;

@Controller
public class OrderAction {
    @Autowired
    private GoodOrderService goodOrderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private WxUserInfoService wxUserService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private GoodService goodService;
    @RequestMapping("/main/order_list")
    @UserInfoFromWebAnnotation
    public String orderList(HttpServletRequest httpServletRequest){
        //0表示全部，1表示待付款，2表示待收获
        String status = httpServletRequest.getParameter("status");
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        Customer customer = customerService.findByOpenId(userInfo.getOpenid());
        Iterable<OrderDetail> orderDetails = null;
        if(status.equals("0")){
            orderDetails = orderDetailService.findByCustomerId(customer.getId());
        }
        if(status.equals("1")){
            List<Integer> statuses = new ArrayList<Integer>();
            statuses.add(1);
            orderDetails = orderDetailService.findByCustomerIdAndStatusIn(customer.getId(),statuses);
        }else if (status.equals("2")) {
            List<Integer> statuses = new ArrayList<Integer>();
            statuses.add(2);
            statuses.add(3);
            orderDetails = orderDetailService.findByCustomerIdAndStatusIn(customer.getId(),statuses);
        }
        List<Map<String, String>> responseOrders = new ArrayList<Map<String,String>>();
        for(OrderDetail orderDetail:orderDetails){
            GoodOrder goodOrder = goodOrderService.findOne(orderDetail.getOrderId());
            Good good = goodService.findOne(orderDetail.getGoodId());
            Map<String, String> responseOrder = new HashMap<String, String>();
            responseOrder.put("goodName", good.getName());
            responseOrder.put("num","1");
            responseOrder.put("flow_price", goodOrder.getFlowPrice()+"");
            responseOrder.put("cost", goodOrder.getCost()+"");
            responseOrder.put("status", goodOrder.getStatus()+"");
            responseOrder.put("type", goodOrder.getType()+"");
            responseOrder.put("orderId", goodOrder.getId());
            responseOrders.add(responseOrder);
        }
        httpServletRequest.setAttribute("status", status);
        httpServletRequest.setAttribute("orders", responseOrders);
        return "main/Orders";
    }
    
    @RequestMapping("/info/order_info")
    public String orderInfo(HttpServletRequest httpServletRequest){
        
        return "info/OrderInfo";
    }
}
