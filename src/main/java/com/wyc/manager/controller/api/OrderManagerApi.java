package com.wyc.manager.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.defineBean.MySimpleDateFormat;
import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.OrderDetail;
import com.wyc.domain.OrderRecord;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.OrderDetailService;
import com.wyc.service.OrderRecordService;
import com.wyc.service.TokenService;
import com.wyc.service.WxUserInfoService;
import com.wyc.wx.domain.Token;
import com.wyc.wx.domain.UserInfo;

@RestController
public class OrderManagerApi {
    @Autowired
    private GoodOrderService goodOrderService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private MySimpleDateFormat mySimpleDateFormat;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WxUserInfoService userInfoService;
    @Autowired
    private TokenService tokenService;
    @RequestMapping("/manager/api/order_handle")
    public Object orderHandle(HttpServletRequest httpServletRequest)throws Exception{
        //1发货，2签收，3退款处理
        String way = httpServletRequest.getParameter("way");
        String orderId = httpServletRequest.getParameter("id");
        String remark = httpServletRequest.getParameter("remark");
        GoodOrder goodOrder = goodOrderService.findOne(orderId);
        //当提交发货行为时当前的状态为“已付款未发货”，处理完之后状态为“已发货未签收”
        if(way.equals("1")){
            if(goodOrder.getStatus()==2){
                String logisticsOrder = httpServletRequest.getParameter("logistics_order");
                String deliveryTime =  httpServletRequest.getParameter("delivery_time");
                goodOrder.setStatus(3);
                goodOrder.setDeliveryTime(new DateTime(mySimpleDateFormat.parse(deliveryTime)));
                goodOrder = goodOrderService.save(goodOrder);
                
                OrderRecord orderRecord = new OrderRecord();
                orderRecord.setLogisticsOrder(logisticsOrder);
                orderRecord.setWay(1);
                orderRecord.setOrderId(orderId);
                orderRecord.setRemark(remark+" 物流单号为："+logisticsOrder+" 发货时间为："+deliveryTime);
                orderRecordService.add(orderRecord);
                return "{'success':true,'content':'成功'}";
                
            }else{
                return "{'success':false,'content':'当前状态不是已付款未发货'}";
            }
            
            //处理行为为签收，当前状态需要是“已付款未签收”，处理之后状态为“已签收”
        }else if (way.equals("2")) {
            if(goodOrder.getStatus()==3){
                goodOrder.setStatus(4);
                String signTime = httpServletRequest.getParameter("sign_time");
                goodOrder.setDeliveryTime(new DateTime(mySimpleDateFormat.parse(signTime)));
                
                
                goodOrderService.save(goodOrder);
                
                
                OrderRecord orderRecord = new OrderRecord();
                orderRecord.setRemark(remark+" 签收时间："+signTime);
                orderRecord.setWay(2);
                orderRecord.setOrderId(orderId);
                
                orderRecordService.add(orderRecord);
                return "{'success':true,'content':'成功'}";
            }else{
                return "{'success':false,'content':'当前状态不是发货未签收'}";
            }
          //当处理行为为退款时，当前状态为退款未处理
        }else if (way.equals("3")) {
            if(goodOrder.getStatus()==5){
                goodOrder.setStatus(6);
                
                String refundDeviveryTime = httpServletRequest.getParameter("refund_devivery_time");
                String refundSignTime = httpServletRequest.getParameter("refund_sign_time");
                String refundAmount = httpServletRequest.getParameter("refund_amount");
                goodOrder.setRefundDeviveryTime(new DateTime(mySimpleDateFormat.parse(refundDeviveryTime)));
                goodOrder.setRefundSignTime(new DateTime(mySimpleDateFormat.parse(refundSignTime)));
                goodOrder.setRefundAmount(Float.parseFloat(refundAmount));
                goodOrderService.save(goodOrder);
                
                OrderRecord orderRecord = new OrderRecord();
                orderRecord.setWay(3);
                orderRecord.setOrderId(orderId);
                orderRecord.setRemark(remark+" 退款发货时间："+refundDeviveryTime+" 退款签收时间："+refundSignTime+" 退款金额："+refundAmount);
                orderRecordService.add(orderRecord);
                return "{'success':true,'content':'成功'}";
            }else{
                return "{'success':false,'content':'当前状态不是退款未完成'}";
            }
        }
        return "{'success':false,'content':'处理失败'}";
    }
    
    @RequestMapping("/manager/api/order_list")
    public Object orderList(HttpServletRequest httpServletRequest){
        //1表示未付款 2表示已付款 未发货 3表示已发货但未签收 4已签收 5退款未处理6退款已处理
        String status = httpServletRequest.getParameter("status");
        List<Map<String, Object>> orderList = new ArrayList<Map<String,Object>>();
        Iterable<GoodOrder> goodOrders = null;
        
        if(status==null||status.equals("")||status.equals("0")){
            goodOrders = goodOrderService.findAllOrderByCreateTimeDesc();  
        }else{
            goodOrders = goodOrderService.findAllByStatusOrderByCreateTimeDesc(Integer.parseInt(status));
        }
        for(GoodOrder goodOrder:goodOrders){
            Map<String, Object> orderMap = new HashMap<String, Object>();
            String goodId = goodOrder.getGoodId();
            Good good = goodService.findOne(goodId);
            orderMap.put("id", goodOrder.getId());
            orderMap.put("cost", goodOrder.getCost());
            orderMap.put("good_name", good.getName());
            orderMap.put("good_id", good.getId());
            orderMap.put("refund_amount", goodOrder.getRefundAmount());
            orderMap.put("flow_price", goodOrder.getFlowPrice());
            orderMap.put("good_price", goodOrder.getGoodPrice());
            if(goodOrder.getRefundTime()!=null){
                orderMap.put("refund_time", mySimpleDateFormat.format(goodOrder.getRefundTime().toDate()));
            }
            if(goodOrder.getRefundDeviveryTime()!=null){
                orderMap.put("refund_devivery_time", mySimpleDateFormat.format(goodOrder.getRefundDeviveryTime().toDate()));
            }
            if(goodOrder.getDeliveryTime()!=null){
                orderMap.put("delivery_time", mySimpleDateFormat.format(goodOrder.getDeliveryTime().toDate()));
            }
            if(goodOrder.getPaymentTime()!=null){
                orderMap.put("payment_time", mySimpleDateFormat.format(goodOrder.getPaymentTime().toDate()));
            }
            if(goodOrder.getRefundSignTime()!=null){
                orderMap.put("refund_sign_time", mySimpleDateFormat.format(goodOrder.getRefundSignTime().toDate()));
            }
            if(goodOrder.getDeliveryTime()!=null){
                orderMap.put("delivery_time", mySimpleDateFormat.format(goodOrder.getDeliveryTime().toDate()));
            }
            if(goodOrder.getPaymentTime()!=null){
                orderMap.put("payment_time", mySimpleDateFormat.format(goodOrder.getPaymentTime().toDate()));

            }
            if(goodOrder.getSignTime()!=null){
                orderMap.put("sign_time", mySimpleDateFormat.format(goodOrder.getSignTime().toDate()));

            }
            orderMap.put("status", goodOrder.getStatus());
            orderMap.put("created_at", mySimpleDateFormat.format(goodOrder.getCreateAt().toDate()));
            orderList.add(orderMap);
        }
        Map<String, List<Map<String, Object>>> response = new HashMap<String, List<Map<String,Object>>>();
        response.put("root", orderList);
        return response;
    }
    
    @RequestMapping("/manager/api/get_customerinfo_by_order")
    public Object customerInfo(HttpServletRequest httpServletRequest){
        String orderId = httpServletRequest.getParameter("order_id");
        OrderDetail orderDetail = orderDetailService.findByOrderId(orderId);
        String customerId = orderDetail.getCustomerId();
        Customer customer = customerService.findOne(customerId);
        String openId = customer.getOpenId();
        UserInfo userInfo = userInfoService.findByOpenid(openId);
        Token token = tokenService.findByIdAndInvalidDateGreaterThan(userInfo.getToken(), new DateTime());
        Map<String, String> responseCustomerInfo = new HashMap<String, String>();
        responseCustomerInfo.put("openId", customer.getOpenId());
        responseCustomerInfo.put("phonenumber", customer.getPhonenumber());
        responseCustomerInfo.put("defaultAddress", customer.getDefaultAddress());
        responseCustomerInfo.put("city", userInfo.getCity());
        responseCustomerInfo.put("country", userInfo.getCountry());
        responseCustomerInfo.put("groupid", userInfo.getGroupid());
        responseCustomerInfo.put("headimgurl", userInfo.getHeadimgurl());
        responseCustomerInfo.put("language", userInfo.getLanguage());
        responseCustomerInfo.put("nickname", userInfo.getNickname());
        responseCustomerInfo.put("province", userInfo.getProvince());
        responseCustomerInfo.put("remark", userInfo.getRemark());
        responseCustomerInfo.put("sex", userInfo.getSex());
        responseCustomerInfo.put("token", userInfo.getToken());
        if(token!=null){
            responseCustomerInfo.put("invalidDate", mySimpleDateFormat.format(token.getInvalidDate().toDate()));
        }
        return responseCustomerInfo;
    }
}
