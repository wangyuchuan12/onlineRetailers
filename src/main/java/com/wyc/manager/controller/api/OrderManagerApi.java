package com.wyc.manager.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.defineBean.MySimpleDateFormat;
import com.wyc.domain.Good;
import com.wyc.domain.GoodOrder;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;

@RestController
public class OrderManagerApi {
    @Autowired
    private GoodOrderService goodOrderService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private MySimpleDateFormat mySimpleDateFormat;
    @RequestMapping("/manager/api/order_list")
    public Object orderList(HttpServletRequest httpServletRequest){
        //1表示未付款 2表示已付款 3表示未发货 4表示已发货但未签收 5已签收 6退款中 7退款成功
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
            orderMap.put("delivery_time", goodOrder.getDeliveryTime());
            orderMap.put("flow_price", goodOrder.getFlowPrice());
            orderMap.put("good_price", goodOrder.getGoodPrice());
            orderMap.put("payment_time", goodOrder.getPaymentTime());
            orderMap.put("sign_time", goodOrder.getSignTime());
            orderMap.put("status", goodOrder.getStatus());
            orderMap.put("created_at", mySimpleDateFormat.format(goodOrder.getCreateAt().toDate()));
            
            orderList.add(orderMap);
        }
        Map<String, List<Map<String, Object>>> response = new HashMap<String, List<Map<String,Object>>>();
        response.put("root", orderList);
        return response;
        
    }
}
