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
import com.wyc.defineBean.MySimpleDateFormat;
import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.MyResource;
import com.wyc.domain.OrderDetail;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerAddressService;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.MyResourceService;
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
    @Autowired
    private CustomerAddressService customerAddressService;
    @Autowired
    private GroupPartakeService groupPartakeService;
    @Autowired
    private MyResourceService myResourceService;
    @Autowired
    private MySimpleDateFormat mySimpleDateFormat;
    @RequestMapping("/main/order_list")
    @UserInfoFromWebAnnotation
    public String orderList(HttpServletRequest httpServletRequest){
        //0表示全部，1表示待付款，2表示待收获
        String status = httpServletRequest.getParameter("status");
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        Customer customer = customerService.findByOpenId(userInfo.getOpenid());
        
        Iterable<GroupPartake> groupPatakes = groupPartakeService.findByCustomerid(customer.getId());
        List<String> orderIds = new ArrayList<String>();
        for(GroupPartake groupPartake:groupPatakes){
            orderIds.add(groupPartake.getOrderId());
        }
        Iterable<GoodOrder> orders = goodOrderService.findAll(orderIds);
        List<Map<String, Object>> responseOrders = new ArrayList<Map<String,Object>>();
        for(GoodOrder goodOrder:orders){
            if(status.equals("0")){
                responseOrders.add(getResponseOrder(goodOrder));
            }else if (status.equals("1")) {
                if(goodOrder.getStatus()==1){
                    responseOrders.add(getResponseOrder(goodOrder));
                }
            }else if (status.equals("2")) {
                if(goodOrder.getStatus()==2||goodOrder.getStatus()==3){
                    responseOrders.add(getResponseOrder(goodOrder));
                }
            }
        }
        httpServletRequest.setAttribute("orders", responseOrders);
        httpServletRequest.setAttribute("status", status);
        return "main/Orders";
    }
    
    private Map<String, Object> getResponseOrder(GoodOrder goodOrder){
        Map<String, Object> responseOrder = new HashMap<String, Object>();
        Good good = goodService.findOne(goodOrder.getGoodId());
        MyResource myResource = myResourceService.findOne(good.getHeadImg());
        responseOrder.put("createTime", mySimpleDateFormat.format(goodOrder.getCreateTime().toDate()));
        responseOrder.put("goodName", good.getName());
        responseOrder.put("goodImg", myResource.getUrl());
        responseOrder.put("cost", goodOrder.getCost());
        responseOrder.put("status", goodOrder.getStatus());
        responseOrder.put("flowPrice", goodOrder.getFlowPrice());
        return responseOrder;
    }
    @RequestMapping("/info/order_info")
    @UserInfoFromWebAnnotation
    public String orderInfo(HttpServletRequest httpServletRequest){
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        String id = httpServletRequest.getParameter("id");
        GoodOrder goodOrder = goodOrderService.findOne(id);
        OrderDetail orderDetail = orderDetailService.findByOrderId(goodOrder.getId());
        String customerId = orderDetail.getCustomerId();
        Customer customer = customerService.findOne(customerId);
        if(userInfo.getOpenid().equals(customer.getOpenId())){
            
        }
        
        return "info/OrderInfo";
    }
}
