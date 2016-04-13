package com.wyc.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.defineBean.MySimpleDateFormat;
import com.wyc.domain.Customer;
import com.wyc.domain.DialogSession;
import com.wyc.domain.DialogSessionItem;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerService;
import com.wyc.service.DialogSessionItemService;
import com.wyc.service.DialogSessionService;
import com.wyc.service.MyResourceService;
import com.wyc.wx.domain.UserInfo;

@RestController
public class ChatApi {
    @Autowired
    private DialogSessionService dialogSessionService;
    @Autowired
    private DialogSessionItemService dialogSessionItemService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private MySimpleDateFormat mySimpleDateFormat;
    @RequestMapping("/api/chat/send_message")
    @UserInfoFromWebAnnotation
    public Object sendMessage(HttpServletRequest httpServletRequest){
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        String adminId = httpServletRequest.getParameter("admin_id");
        String content = httpServletRequest.getParameter("content");
        String goodId = httpServletRequest.getParameter("good_id");
        String orderId = httpServletRequest.getParameter("order_id");
        String type = httpServletRequest.getParameter("type");
        Customer customer = customerService.findByOpenId(userInfo.getOpenid());
        DialogSession dialogSession = dialogSessionService.findByCustomerIdAndAdminId(customer.getId(), adminId);
        if(dialogSession==null){
            dialogSession = new DialogSession();
            dialogSession.setAdminId(adminId);
            dialogSession.setCustomerId(customer.getId());
            dialogSession = dialogSessionService.add(dialogSession);
        }
        String headImg = userInfo.getHeadimgurl();
        DialogSessionItem dialogSessionItem = new DialogSessionItem();
        dialogSessionItem.setContent(content);
        dialogSessionItem.setCustomerId(customer.getId());
        dialogSessionItem.setDateTime(new DateTime());
        dialogSessionItem.setDialogSessionId(dialogSession.getId());
        dialogSessionItem.setGoodId(goodId);
        dialogSessionItem.setHeadImg(headImg);
        dialogSessionItem.setOrderId(orderId);
        dialogSessionItem.setRole(DialogSessionItem.CUSTOMER_ROLE);
        dialogSessionItem.setType(type);
        dialogSessionItem = dialogSessionItemService.add(dialogSessionItem);
        Map<String, String> responseObj = new HashMap<String, String>();
        responseObj.put("content", content);
        responseObj.put("headImg", headImg);
        
        responseObj.put("dateTime", mySimpleDateFormat.format(dialogSessionItem.getDateTime().toDate()));
        return responseObj;
    }
}
