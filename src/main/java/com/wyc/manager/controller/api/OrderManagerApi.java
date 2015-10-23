package com.wyc.manager.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wyc.defineBean.MySimpleDateFormat;
import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.OrderDetail;
import com.wyc.domain.OrderRecord;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.GroupPartakeService;
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
    @Autowired
    private GroupPartakeService groupPartakeService;
    final static Logger logger = LoggerFactory.getLogger(OrderManagerApi.class);
    @RequestMapping("/manager/api/order_handle")
    public Object orderHandle(HttpServletRequest httpServletRequest)throws Exception{
        return null;
    }
    
    @RequestMapping("/manager/api/order_list")
    public Object orderList(HttpServletRequest httpServletRequest){
       return null;
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
    @RequestMapping("/manager/api/get_orderdetail_by_order")
    public Object orderDetail(HttpServletRequest httpServletRequest){
        String orderId = httpServletRequest.getParameter("order_id");
        Iterable<OrderRecord> orderRecords = orderRecordService.findAllByOrderId(orderId);
        List<Map<String, Object>> responseOrderRecords = new ArrayList<Map<String,Object>>();
        for(OrderRecord orderRecord:orderRecords){
            Map<String, Object> responseOrderRecord = new HashMap<String, Object>();
            responseOrderRecord.put("createAt", mySimpleDateFormat.format(orderRecord.getCreateAt().toDate()));
            responseOrderRecord.put("id", orderRecord.getId());
            responseOrderRecord.put("logisticsOrder", orderRecord.getLogisticsOrder());
            responseOrderRecord.put("orderId", orderRecord.getOrderId());
            responseOrderRecord.put("remark", orderRecord.getRemark());
            responseOrderRecord.put("way", orderRecord.getWay());
            responseOrderRecords.add(responseOrderRecord);
        }
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("root", responseOrderRecords);
        return response;
    }
    
    
    @RequestMapping("/manager/api/get_groupdetail_by_order")
    public Object groupDetail(HttpServletRequest httpServletRequest){
        String orderId = httpServletRequest.getParameter("order_id");
        OrderDetail orderDetail = orderDetailService.findByOrderId(orderId);
        String groupId = orderDetail.getGroupId();
        logger.debug("the groupid is {}"+groupId);
        List<Map<String, Object>> responseGroupDetails = new ArrayList<Map<String,Object>>();
        Map<String, Object> response = new HashMap<String, Object>();
        
        if(groupId!=null){
            Iterable<GroupPartake> groupPartakes = groupPartakeService.findAllByGroupIdOrderByRoleAsc(groupId);
            for(GroupPartake groupPartake:groupPartakes){
                Map<String, Object> responseGroupDetail = new HashMap<String, Object>();
                String customerId = groupPartake.getCustomerid();
                Customer customer = customerService.findOne(customerId);
                String openId = customer.getOpenId();
                UserInfo userInfo = userInfoService.findByOpenid(openId);
                responseGroupDetail.put("datetime", mySimpleDateFormat.format(groupPartake.getDateTime().toDate()));
                responseGroupDetail.put("id", groupPartake.getId());
                responseGroupDetail.put("role", groupPartake.getRole());
                responseGroupDetail.put("orderId", orderId);
                responseGroupDetail.put("defaultAdress", customer.getDefaultAddress());
                responseGroupDetail.put("phonenumber", customer.getPhonenumber());
                responseGroupDetail.put("city", userInfo.getCity());
                
                responseGroupDetail.put("country", userInfo.getCountry());
                responseGroupDetail.put("groupid", userInfo.getGroupid());
                responseGroupDetail.put("headimgurl", userInfo.getHeadimgurl());
                responseGroupDetail.put("language", userInfo.getLanguage());
                responseGroupDetail.put("userinfoId", userInfo.getId());
                responseGroupDetail.put("nickname", userInfo.getNickname());
                responseGroupDetail.put("province", userInfo.getProvince());
                responseGroupDetail.put("remark", userInfo.getRemark());
                responseGroupDetail.put("sex", userInfo.getSex());
                responseGroupDetail.put("subscribe", userInfo.getSubscribe());
                responseGroupDetail.put("subscribeTime", userInfo.getSubscribe_time());
                responseGroupDetail.put("token", userInfo.getToken());
                responseGroupDetail.put("unionid", userInfo.getUnionid());
                responseGroupDetail.put("type",groupPartake.getType());
                responseGroupDetails.add(responseGroupDetail);
            }
        }
        response.put("root",responseGroupDetails);
        return response;
    }
}
