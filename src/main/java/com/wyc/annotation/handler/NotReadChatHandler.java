package com.wyc.annotation.handler;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

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

public class NotReadChatHandler implements Handler{
    @Autowired
    private DialogSessionItemReadService dialogSessionItemReadService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DialogSessionService dialogSessionService;
    @Autowired
    private DialogSessionItemService dialogSessionItemService;
    @Override
    public Object handle(HttpServletRequest httpServletRequest)
            throws Exception {
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        Customer customer = customerService.findByOpenId(userInfo.getOpenid());
        Iterable<DialogSession> dialogSessions = dialogSessionService.findAllByCustomerId(customer.getId());
        Map<Object, Object> responseData = new HashMap<Object, Object>();
        List<Object> notReadItems = new ArrayList<Object>();
        for(DialogSession dialogSession:dialogSessions){
            Iterable<DialogSessionItem> dialogSessionItems = dialogSessionItemService.findAllByDialogSessionIdOrderByDateTimeAsc(dialogSession.getId());
            
            for(DialogSessionItem dialogSessionItem:dialogSessionItems){
                DialogSessionItemRead dialogSessionItemRead = dialogSessionItemReadService.findByCustomerIdAndRoleAndItemId(customer.getId(), DialogSessionItem.CUSTOMER_ROLE, dialogSessionItem.getId());
                if(dialogSessionItemRead==null||dialogSessionItemRead.getCount()==0){
                    notReadItems.add(dialogSessionItem.getId());
                }
            }
        }
        responseData.put("notReadItems", notReadItems);
        responseData.put("notReadCount", notReadItems.size());
        httpServletRequest.setAttribute("chat", responseData);
        return null;
        
    }

    @Override
    public Class<? extends Handler>[] extendHandlers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAnnotation(Annotation annotation) {
        // TODO Auto-generated method stub
        
    }

}
