package com.wyc.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.wyc.domain.DialogSessionItemRead;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerService;
import com.wyc.service.DialogSessionItemReadService;
import com.wyc.service.DialogSessionItemService;
import com.wyc.service.DialogSessionService;
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
    @Autowired
    private DialogSessionItemReadService dialogSessionItemReadService;
    
    @RequestMapping("/api/chat/select_not_read")
    @UserInfoFromWebAnnotation
    public Object selectNotRead(HttpServletRequest httpServletRequest){
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        String adminId = httpServletRequest.getParameter("admin_id");
        Customer customer = customerService.findByOpenId(userInfo.getOpenid());
        DialogSession dialogSession = dialogSessionService.findByCustomerIdAndAdminId(customer.getId(), adminId);
        Iterable<DialogSessionItem> dialogSessionItems = dialogSessionItemService.findAllByDialogSessionIdOrderByDateTimeAsc(dialogSession.getId());
        Map<Object, Object> responseData = new HashMap<Object, Object>();
        List<Object> notReadIds = new ArrayList<Object>();
        for(DialogSessionItem dialogSessionItem:dialogSessionItems){
            DialogSessionItemRead dialogSessionItemRead = dialogSessionItemReadService.findByCustomerIdAndRoleAndItemId(customer.getId(), DialogSessionItem.CUSTOMER_ROLE, dialogSessionItem.getId());
            if(dialogSessionItemRead==null||dialogSessionItemRead.getCount()==0){
                notReadIds.add(dialogSessionItem.getId());
            }
        }
        responseData.put("notReadItemIds", notReadIds);
        responseData.put("notReadCount", notReadIds.size());
        return responseData;
    }
    
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
        DialogSessionItemRead dialogSessionItemRead = new DialogSessionItemRead();
        dialogSessionItemRead.setCount(1);
        dialogSessionItemRead.setCustomerId(customer.getId());
        dialogSessionItemRead.setDateTime(new DateTime());
        dialogSessionItemRead.setItemId(dialogSessionItem.getId());
        dialogSessionItemRead.setRole(DialogSessionItem.CUSTOMER_ROLE);
        dialogSessionItemReadService.add(dialogSessionItemRead);
        Map<String, String> responseObj = new HashMap<String, String>();
        responseObj.put("content", content);
        responseObj.put("headImg", headImg);
        
        responseObj.put("dateTime", mySimpleDateFormat.format(dialogSessionItem.getDateTime().toDate()));
        responseObj.put("role", dialogSessionItem.getRole()+"");
        return responseObj;
    }
}