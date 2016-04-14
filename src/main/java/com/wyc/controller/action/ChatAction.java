package com.wyc.controller.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyc.annotation.AccessTokenAnnotation;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.Business;
import com.wyc.domain.Customer;
import com.wyc.domain.DialogSession;
import com.wyc.domain.DialogSessionItem;
import com.wyc.domain.DialogSessionItemRead;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.BusinessService;
import com.wyc.service.CustomerService;
import com.wyc.service.DialogSessionItemReadService;
import com.wyc.service.DialogSessionItemService;
import com.wyc.service.DialogSessionService;
import com.wyc.wx.domain.UserInfo;

@Controller
public class ChatAction {
    @Autowired
    private DialogSessionService dialogSessionService;
    @Autowired
    private DialogSessionItemService dialogSessionItemService;
    @Autowired
    private CustomerService cusomterService;
    @Autowired
    private DialogSessionItemReadService dialogSessionItemReadService;
    @Autowired
    private BusinessService businessService;
    @RequestMapping("/info/chat_list")
    @UserInfoFromWebAnnotation
    public String chatList(HttpServletRequest httpServletRequest)throws Exception{
        MyHttpServletRequest  myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        Customer customer = cusomterService.findByOpenId(userInfo.getOpenid());
        Iterable<DialogSession> dialogSessions = dialogSessionService.findAllByCustomerIdOrderByUpdateAtDesc(customer.getId());
        List<Map<String, Object>> responseDatas = new ArrayList<Map<String,Object>>();
        for(DialogSession dialogSession:dialogSessions){
            String adminId = dialogSession.getAdminId();
            Map<String, Object> responseData = new HashMap<String, Object>();
            Iterable<DialogSessionItem> dialogSessionItems = dialogSessionItemService.findAllByCustomerIdAndDialogSessionIdAndRole(customer.getId(), dialogSession.getId() ,DialogSessionItem.CUSTOMER_ROLE);
            int notReadCount = 0;
            for(DialogSessionItem dialogSessionItem:dialogSessionItems){
                DialogSessionItemRead dialogSessionItemRead = dialogSessionItemReadService.findByCustomerIdAndRoleAndItemId(customer.getId(), DialogSessionItem.CUSTOMER_ROLE, dialogSessionItem.getId());
                if(dialogSessionItemRead==null||dialogSessionItemRead.getCount()==0){
                    notReadCount++;
                }
            }
            DialogSessionItem lastDialogSessionItem = dialogSessionItemService.selectLastItemByDialogSessionId(dialogSession.getId());
            Business business = businessService.findByAdminId(adminId);
            responseData.put("business", business);
            responseData.put("notReadCount", notReadCount);
            responseData.put("lastItem", lastDialogSessionItem);
            responseDatas.add(responseData);
        }
        httpServletRequest.setAttribute("notReadDatas", responseDatas);
        return "info/chatList";
    }
    
    @RequestMapping("/info/chat_view")
    @UserInfoFromWebAnnotation
    @AccessTokenAnnotation
    public String chatView(HttpServletRequest httpRequest)throws Exception{
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest) httpRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        Customer customer = cusomterService.findByOpenId(userInfo.getOpenid());
        String adminId = myHttpServletRequest.getParameter("admin_id");
        DialogSession dialogSession = dialogSessionService.findByCustomerIdAndAdminId(customer.getId(),adminId);
        if(dialogSession!=null){
            Iterable<DialogSessionItem> dialogSessionItems = dialogSessionItemService.findAllByDialogSessionIdOrderByDateTimeAsc(dialogSession.getId());
            myHttpServletRequest.setAttribute("dialogSession", dialogSession);
            myHttpServletRequest.setAttribute("dialogSessionItems", dialogSessionItems);
            for(DialogSessionItem dialogSessionItem:dialogSessionItems){
                DialogSessionItemRead dialogSessionItemRead = dialogSessionItemReadService.findByCustomerIdAndRoleAndItemId(customer.getId(),DialogSessionItem.CUSTOMER_ROLE,dialogSessionItem.getId());
                if(dialogSessionItemRead!=null){
                    dialogSessionItemRead.setDateTime(new DateTime());
                    dialogSessionItemRead.setCount(dialogSessionItemRead.getCount()+1);
                    dialogSessionItemReadService.save(dialogSessionItemRead);
                }else{
                    dialogSessionItemRead = new DialogSessionItemRead();
                    dialogSessionItemRead.setDateTime(new DateTime());
                    dialogSessionItemRead.setCustomerId(customer.getId());
                    dialogSessionItemRead.setRole(DialogSessionItem.CUSTOMER_ROLE);
                    dialogSessionItemRead.setCount(1);
                    
                    dialogSessionItemRead.setItemId(dialogSessionItem.getId());
                    dialogSessionItemReadService.add(dialogSessionItemRead);
                }
            }
            
        }
        String type = httpRequest.getParameter("type");
        String goodId = httpRequest.getParameter("goodId");
        String orderId = httpRequest.getParameter("orderId");
        httpRequest.setAttribute("adminId", adminId);
        httpRequest.setAttribute("type", type);
        httpRequest.setAttribute("goodId", goodId);
        httpRequest.setAttribute("orderId", orderId);
        
        return "info/chat";
    }

}
