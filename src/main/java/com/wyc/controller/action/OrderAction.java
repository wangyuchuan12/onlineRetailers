package com.wyc.controller.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.defineBean.MySimpleDateFormat;
import com.wyc.domain.City;
import com.wyc.domain.Customer;
import com.wyc.domain.CustomerAddress;
import com.wyc.domain.Good;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.GroupPartakeDeliver;
import com.wyc.domain.GroupPartakePayment;
import com.wyc.domain.MyResource;
import com.wyc.domain.OrderDetail;
import com.wyc.domain.TemporaryData;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CityService;
import com.wyc.service.CustomerAddressService;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.GroupPartakeDeliverService;
import com.wyc.service.GroupPartakePaymentService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.MyResourceService;
import com.wyc.service.OrderDetailService;
import com.wyc.service.TemporaryDataService;
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
    @Autowired
    private CityService cityService;
    @Autowired
    private TemporaryDataService temporaryDataService;
    @Autowired
    private GroupPartakeDeliverService groupPartakeDeliverService;
    @Autowired
    private GroupPartakePaymentService groupPartakePaymentService;
    final static Logger logger = LoggerFactory.getLogger(OrderAction.class);
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
            logger.debug("the orderId is {}",groupPartake.getOrderId());
        }
        Iterable<GoodOrder> orders = goodOrderService.findAll(orderIds);
        List<Map<String, Object>> responseOrders = new ArrayList<Map<String,Object>>();
        for(GoodOrder goodOrder:orders){
            //1表示未付款 2表示已付款 未发货 3表示已发货但未签收 4已签收 5退款未处理6退款已处理
            
            Map<String, Object> responseOrder = getResponseOrder(goodOrder);
            if(status.equals("0")){
                for(GroupPartake groupPartake:groupPatakes){
                    if(groupPartake.getOrderId().equals(goodOrder.getId())){
                        GroupPartakeDeliver groupPartakeDeliver = groupPartakeDeliverService.findByGroupPartakeId(groupPartake.getId());
                        GroupPartakePayment groupPartakePayment = groupPartakePaymentService.findByGroupPartakeId(groupPartake.getId());
                        if(groupPartakePayment.getStatus()==0){
                            responseOrder.put("status", 1);
                        }else if (groupPartakePayment.getStatus()==1&&groupPartakeDeliver.getStatus()==0) {
                            responseOrder.put("status", 2);
                        }else if (groupPartakePayment.getStatus()==1&&groupPartakeDeliver.getStatus()==1) {
                            responseOrder.put("status", 3);
                        }else if (groupPartakePayment.getStatus()==1&&groupPartakeDeliver.getStatus()==2) {
                            responseOrder.put("status", 4);
                        }else if (groupPartakePayment.getStatus()==2) {
                            responseOrder.put("status", 5);
                        }else if (groupPartakePayment.getStatus()==3) {
                            responseOrder.put("status", 6);
                        }
                    }
                }
                responseOrders.add(responseOrder);
            }else if (status.equals("1")) {
                //购买失败时添加的数据
            }else if (status.equals("2")) {
                for(GroupPartake groupPartake:groupPatakes){
                    if(groupPartake.getOrderId().equals(goodOrder.getId())){
                        GroupPartakeDeliver groupPartakeDeliver = groupPartakeDeliverService.findByGroupPartakeId(groupPartake.getId());
                        GroupPartakePayment groupPartakePayment = groupPartakePaymentService.findByGroupPartakeId(groupPartake.getId());
                        if(groupPartakePayment.getStatus()==0){
                            responseOrder.put("status", 1);
                        }else if (groupPartakePayment.getStatus()==1&&groupPartakeDeliver.getStatus()==0) {
                            responseOrder.put("status", 2);
                        }else if (groupPartakePayment.getStatus()==1&&groupPartakeDeliver.getStatus()==1) {
                            responseOrder.put("status", 3);
                        }else if (groupPartakePayment.getStatus()==1&&groupPartakeDeliver.getStatus()==2) {
                            responseOrder.put("status", 4);
                        }else if (groupPartakePayment.getStatus()==2) {
                            responseOrder.put("status", 5);
                        }else if (groupPartakePayment.getStatus()==3) {
                            responseOrder.put("status", 6);
                        }
                        if(groupPartakeDeliver.getStatus()==1){
                            responseOrders.add(responseOrder);
                            break;
                        }
                    }
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
        responseOrder.put("flowPrice", goodOrder.getFlowPrice());
        responseOrder.put("id", goodOrder.getId());

        return responseOrder;
    }
    
    @RequestMapping("/info/last_order")
    @UserInfoFromWebAnnotation
    public String skipLastOrder(HttpServletRequest httpServletRequest){
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        TemporaryData lastOrderTemporaryData = temporaryDataService.findByMyKeyAndNameAndStatus(userInfo.getOpenid(),"lastOrder" , 1);
        return "redirect:/info/order_info?id="+lastOrderTemporaryData.getValue();
    }
    @RequestMapping("/info/order_info")
    @UserInfoFromWebAnnotation
    public String orderInfo(HttpServletRequest httpServletRequest){
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        String id = httpServletRequest.getParameter("id");
        GoodOrder goodOrder = goodOrderService.findOne(id);
        OrderDetail orderDetail = orderDetailService.findByOrderId(goodOrder.getId());
        Customer customer = customerService.findByOpenId(userInfo.getOpenid());
        Map<String, Object> orderResponse = getResponseOrder(goodOrder);
        if(customer!=null){
            GroupPartake groupPartake = groupPartakeService.findByCustomeridAndGroupId(customer.getId(), orderDetail.getGroupId());
            GroupPartakeDeliver groupPartakeDeliver = groupPartakeDeliverService.findByGroupPartakeId(groupPartake.getId());
            GroupPartakePayment groupPartakePayment = groupPartakePaymentService.findByGroupPartakeId(groupPartake.getId());
            if(groupPartakePayment.getStatus()==0){
                orderResponse.put("status", 1);
            }else if (groupPartakePayment.getStatus()==1&&groupPartakeDeliver.getStatus()==0) {
                orderResponse.put("status", 2);
            }else if (groupPartakePayment.getStatus()==1&&groupPartakeDeliver.getStatus()==1) {
                orderResponse.put("status", 3);
            }else if (groupPartakePayment.getStatus()==1&&groupPartakeDeliver.getStatus()==2) {
                orderResponse.put("status", 4);
            }else if (groupPartakePayment.getStatus()==2) {
                orderResponse.put("status", 5);
            }else if (groupPartakePayment.getStatus()==3) {
                orderResponse.put("status", 6);
            }
            Good good = goodService.findOne(goodOrder.getGoodId());
            MyResource myResource = myResourceService.findOne(good.getHeadImg());
            orderResponse.put("goodImg", myResource.getUrl());
            orderResponse.put("cost", goodOrder.getCost());
            orderResponse.put("type", goodOrder.getType());
            orderResponse.put("id", goodOrder.getId());
            orderResponse.put("createTime", mySimpleDateFormat.format(goodOrder.getCreateTime().toDate()));
            
            CustomerAddress customerAddress = customerAddressService.findOne(groupPartake.getCustomerAddress());
            orderResponse.put("address", customerAddress.getContent());
            orderResponse.put("recipient", customerAddress.getName());
            logger.debug("recipient:"+customerAddress.getName());
            orderResponse.put("phonenumber", customerAddress.getPhonenumber());
            logger.debug("phonenumber:"+customerAddress.getPhonenumber());
            City city = cityService.findOne(customerAddress.getCity());
            orderResponse.put("area", city.getName());
            orderResponse.put("goodName", good.getName());
            orderResponse.put("goodPrice",goodOrder.getCost());
            orderResponse.put("code", goodOrder.getCode());
            orderResponse.put("groupId", orderDetail.getGroupId());
        }
        httpServletRequest.setAttribute("order", orderResponse);
        return "info/OrderInfo";
    }
}
