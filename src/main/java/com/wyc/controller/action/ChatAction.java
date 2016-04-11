package com.wyc.controller.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyc.annotation.AccessTokenAnnotation;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.Customer;
import com.wyc.domain.DialogSession;
import com.wyc.domain.DialogSessionItem;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerService;
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
