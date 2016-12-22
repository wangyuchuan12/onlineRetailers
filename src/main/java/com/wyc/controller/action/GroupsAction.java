package com.wyc.controller.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wyc.annotation.AfterHandlerAnnotation;
import com.wyc.annotation.BeforeNativeHandlerAnnotation;
import com.wyc.annotation.JsApiTicketAnnotation;
import com.wyc.annotation.NowPageRecordAnnotation;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.annotation.WxConfigAnnotation;
import com.wyc.annotation.handler.AfterGoodTypeHandler;
import com.wyc.annotation.handler.BeforeGoodTypeHandler;
import com.wyc.annotation.handler.NotReadChatHandler;
import com.wyc.defineBean.MySimpleDateFormat;
import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.HotGroup;
import com.wyc.domain.OrderDetail;
import com.wyc.domain.TemporaryData;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.HotGroupService;
import com.wyc.service.MyResourceService;
import com.wyc.service.OrderDetailService;
import com.wyc.service.TemporaryDataService;
import com.wyc.service.WxUserInfoService;
import com.wyc.wx.domain.UserInfo;

@Controller
public class GroupsAction {
    @Autowired
    private GoodGroupService goodGroupService;
    @Autowired
    private GroupPartakeService groupPartakeService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private MyResourceService myResourceService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Autowired
    private MySimpleDateFormat mySimpleDateFormat;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private TemporaryDataService temporaryDataService;
    @Autowired
    private GoodOrderService goodOrderService;
    @Autowired
    private HotGroupService hotGroupService;
    final static Logger logger = LoggerFactory.getLogger(GroupsAction.class);

    @RequestMapping("/main/group_list")
    @JsApiTicketAnnotation
	@UserInfoFromWebAnnotation
	@WxConfigAnnotation
	@NowPageRecordAnnotation(page=3)
    @BeforeNativeHandlerAnnotation(hanlerClasses={BeforeGoodTypeHandler.class})
    @AfterHandlerAnnotation(hanlerClasses={AfterGoodTypeHandler.class,NotReadChatHandler.class})
    public String groupList(HttpServletRequest servletRequest) throws Exception {
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest) servletRequest;
        String openId = null;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        openId = userInfo.getOpenid();
        logger.debug("get openid {}", openId);
        if (openId == null || openId.trim().equals("")) {
            return "main/Groups";
        }
        Customer customer = customerService.findByOpenId(openId);
        logger.debug("get the customer is {}", customer);
        if (customer == null) {
            return "main/Groups";
        }
        String customerId = customer.getId();
        Iterable<GroupPartake> groupPartakes = groupPartakeService
                .findByCustomerid(customerId);
        List<String> groupIds = new ArrayList<String>();
        for (GroupPartake groupPartake : groupPartakes) {
            if(groupPartake.getGroupId()!=null){
                groupIds.add(groupPartake.getGroupId());
            }
        }
        logger.debug("current customer group id list is {}", groupIds);
        if(groupIds.size()>0){
            Iterable<GoodGroup> goodGroups = goodGroupService.findAllByIdInOrderByCreateAtDesc(groupIds);
            List<Map<String, String>> responseGroups = new ArrayList<Map<String, String>>();
            for (GoodGroup goodGroup : goodGroups) {
                Map<String, String> responseGroup = new HashMap<String, String>();
                goodGroup = checkTimeout(goodGroup);
                OrderDetail orderDetail = orderDetailService.findByGruopId(goodGroup.getId());
                responseGroup.put("result", goodGroup.getResult() + "");
                Good good = goodService.findOne(goodGroup.getGoodId());
                responseGroup.put("name", good.getName());
                responseGroup.put("head_img",
                        myResourceService.findOne(good.getHeadImg()).getUrl());
                responseGroup.put("total_price", goodGroup.getTotalPrice() + "");
                responseGroup.put("group_id", goodGroup.getId());
                responseGroup.put("order_id", orderDetail.getOrderId());
                responseGroup.put("adminId", good.getAdminId());
                responseGroups.add(responseGroup);
            }
            servletRequest.setAttribute("groups", responseGroups);
        }
        
        return "main/Groups";
    }
    
    private GoodGroup checkTimeout(GoodGroup goodGroup){
        if(goodGroup.getResult()==1){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(goodGroup.getStartTime().toDate());
            calendar.add(Calendar.HOUR, goodGroup.getTimeLong());
            if(calendar.getTime().getTime()<new Date().getTime()){
                goodGroup.setResult(0);
                goodGroup = goodGroupService.update(goodGroup);
                OrderDetail orderDetail = orderDetailService.findByGruopId(goodGroup.getId());
                GoodOrder goodOrder = goodOrderService.findOne(orderDetail.getOrderId());
                goodOrder.setStatus(5);
                goodOrderService.save(goodOrder);
            }
        }
        return goodGroup;
    }
 //   @RequestMapping("/info/takepart_group")
    @UserInfoFromWebAnnotation
    @Transactional
    public String takepartGroup(HttpServletRequest httpServletRequest)
            throws Exception {
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest) httpServletRequest;
        UserInfo requestUser = myHttpServletRequest.getUserInfo();
        Customer customer = customerService.findByOpenId(requestUser
                .getOpenid());
        String id = myHttpServletRequest.getParameter("id");
        String type = "2";
        GroupPartake groupPartake = groupPartakeService
                .findByCustomeridAndGroupId(customer.getId(), id);
        if (groupPartake != null) {
            return null;
        } else {
            groupPartake = new GroupPartake();
            groupPartake.setCustomerid(customer.getId());
            groupPartake.setDateTime(new DateTime());
            groupPartake.setGroupId(id);
            int count = groupPartakeService.countByGroupId(id);
            if (count == 0) {
                groupPartake.setRole(1);
            } else if (count == 1) {
                groupPartake.setRole(2);
            } else {
                groupPartake.setRole(3);
            }
            OrderDetail orderDetail = orderDetailService.findByGruopId(id);
            groupPartake.setType(Integer.parseInt(type));
            groupPartake.setOrderId(orderDetail.getOrderId());
            GoodGroup goodGroup = goodGroupService.findOne(id);
            int groupNum = goodGroup.getNum();
            logger.debug("take part group and the count is {} , groupNum is {}" , count , groupNum);
            if (groupNum == count) {
                return null;
            } else if (groupNum == count + 1) {
                goodGroup.setResult(2);
            }
            goodGroupService.update(goodGroup);
            groupPartakeService.add(groupPartake);
            return groupInfo(myHttpServletRequest);
        }

    }
    
    @UserInfoFromWebAnnotation
    @RequestMapping("/info/lastest_group_info")
    public String skipToLatestGroupInfo(HttpServletRequest httpServletRequest){
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        TemporaryData lastGroupId = null;
        for(int i = 0 ;i<500;i++){
            lastGroupId = temporaryDataService.findByMyKeyAndNameAndStatus(userInfo.getOpenid(),"lastGroupId" , 1);
            if(lastGroupId!=null){
                lastGroupId.setStatus(0);
                temporaryDataService.save(lastGroupId);
                break;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                logger.error("has an error of thread sleep :{}",e);
            }
        }
        GoodGroup goodGroup = goodGroupService.findOne(lastGroupId.getValue());
        return "redirect:/info/group_info2?prompt=true&id="+goodGroup.getId()+"&token="+myHttpServletRequest.getToken().getId();
    }
    
    @RequestMapping("/info/group_info2")
    @JsApiTicketAnnotation
	@UserInfoFromWebAnnotation
	@WxConfigAnnotation
	@NowPageRecordAnnotation(page=4)
    @BeforeNativeHandlerAnnotation(hanlerClasses={BeforeGoodTypeHandler.class})
    @AfterHandlerAnnotation(hanlerClasses={AfterGoodTypeHandler.class,NotReadChatHandler.class})
    public String groupInfo(HttpServletRequest httpServletRequest)
            throws Exception {
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest) httpServletRequest;
        String prompt = httpServletRequest.getParameter("prompt");
        
        UserInfo requestUser = myHttpServletRequest.getUserInfo();
        String id = httpServletRequest.getParameter("id");
        GoodGroup goodGroup = goodGroupService.findOne(id);
        goodGroup = checkTimeout(goodGroup);
        logger.debug("the group id is {}", id);
        int result = goodGroup.getResult();
        
        if(prompt!=null&&result==1){
        	httpServletRequest.setAttribute("prompt", true);
        }
        Iterable<GroupPartake> groupPartakes = groupPartakeService
                .findAllByGroupIdOrderByDateTime(id);
        String goodId = goodGroup.getGoodId();
        Good good = goodService.findOne(goodId);
        myHttpServletRequest.setAttribute("goodType", good.getGoodType());
        String goodName = good.getName();
        String headImg = myResourceService.findOne(good.getHeadImg()).getUrl();
        int groupNum = goodGroup.getNum();
        BigDecimal totalPrice = goodGroup.getTotalPrice();
        List<Map<String, String>> groupMembers = new ArrayList<Map<String, String>>();
        Integer role = 0;
        
        for (GroupPartake groupPartake : groupPartakes) {
            Map<String, String> groupMember = new HashMap<String, String>();
 
            String openid = groupPartake.getOpenid();
            groupMember.put("name",groupPartake.getNickname());
            groupMember.put("headImg", groupPartake.getHeadimgurl());
            groupMember.put("role", groupPartake.getRole() + "");
            groupMember.put("datetime", mySimpleDateFormat.format(groupPartake
                    .getDateTime().toDate()));
            groupMember.put("groupPartakeId", groupPartake.getId());
            groupMember.put("isInsteadOfReceiving", groupPartake.getIsInsteadOfReceiving()+"");
            groupMembers.add(groupMember);
            if (openid.equals(requestUser.getOpenid())) {
                role = groupPartake.getRole();
            }
        }

        Map<String, Object> groupInfoMap = new HashMap<String, Object>();
        groupInfoMap.put("result", result);
        groupInfoMap.put("goodName", goodName);
        groupInfoMap.put("headImg", headImg);
        groupInfoMap.put("title", good.getTitle());
        groupInfoMap.put("id", id);
        groupInfoMap.put("goodId", good.getId());
        groupInfoMap.put("groupNum", groupNum);
        groupInfoMap.put("totalPrice", totalPrice);
        groupInfoMap.put("groupPartake", groupMembers);
        
        groupInfoMap.put("startTime",
                mySimpleDateFormat.format(goodGroup.getStartTime().toDate()));
        groupInfoMap.put("timeLong", goodGroup.getTimeLong());
        groupInfoMap.put("role", role);
        groupInfoMap.put("notice",good.getNotice());
        groupInfoMap.put("adminId", good.getAdminId());
        groupInfoMap.put("stock", good.getStock());
        groupInfoMap.put("salesVolume", good.getSalesVolume());
        groupInfoMap.put("allowInsteadOfRelief", good.getAllowInsteadOfRelief());
        groupInfoMap.put("forceInsteadOfRelief", good.getForceInsteadOfRelief());
        groupInfoMap.put("isReceiveGoodsTogether", goodGroup.getIsReceiveGoodsTogether());
        groupInfoMap.put("insteadOfRelief", good.getInsteadOfRelief());
        groupInfoMap.put("goodPrice", goodGroup.getGoodPrice());
        httpServletRequest.setAttribute("groupInfo", groupInfoMap);
        TemporaryData temporaryData = temporaryDataService.findByMyKeyAndNameAndStatus(requestUser.getOpenid(), "nowgroup" , 1);
        if(temporaryData==null){
            temporaryData = new TemporaryData();
            temporaryData.setMykey(requestUser.getOpenid());
            temporaryData.setName("nowgroup");
            temporaryData.setValue(id);
            temporaryData.setKeyName("openid");
            temporaryDataService.add(temporaryData);
            
        }else{
            temporaryData.setValue(id);
            temporaryDataService.save(temporaryData);
        }
        return "info/GroupInfo";
    }

    @RequestMapping("/info/trade_flow_info")
    public String tradeFlowInfo(HttpServletRequest httpServletRequest)
            throws Exception {

        return "info/TradeFlowInfo";
    }
    @WxConfigAnnotation
    @JsApiTicketAnnotation
    @RequestMapping("/info/hot_group")
    public String hotGroups(HttpServletRequest httpServletRequest){
    	
    	List<GoodGroup> goodGroups = goodGroupService.findAllHotGroups();
    	
    	List<Map<String, Object>> responseGroups = new ArrayList<>();
    	
    	
    	httpServletRequest.setAttribute("title", "晨曦拼货商城");
    	httpServletRequest.setAttribute("instruction", "风靡全国的拼货商城，优质商品新鲜直供，快来一起拼团吧");
    	httpServletRequest.setAttribute("img", "http://www.chengxihome.com/img/logo.jpg");
    	for(GoodGroup goodGroup:goodGroups){
    		checkTimeout(goodGroup);
    		
    		if(goodGroup.getResult()==1){
	    		Map<String, Object> responseGroup = new HashMap<>();
	    		Good good = goodService.findOne(goodGroup.getGoodId());
	    		responseGroup.put("group_id",goodGroup.getId());
	    		responseGroup.put("result", goodGroup.getResult() + "");
	            responseGroup.put("name", good.getName());
	            responseGroup.put("head_img",
	                    myResourceService.findOne(good.getHeadImg()).getUrl());
	            responseGroup.put("total_price", goodGroup.getTotalPrice() + "");
	            responseGroup.put("group_id", goodGroup.getId());
	            responseGroup.put("adminId", good.getAdminId());
	            responseGroup.put("group_num",goodGroup.getNum()+"");
	            responseGroup.put("partake_num", groupPartakeService.countByGroupId(goodGroup.getId())+"");
	            
	            responseGroup.put("startTime",
	                    mySimpleDateFormat.format(goodGroup.getStartTime().toDate()));
	            responseGroup.put("timeLong", goodGroup.getTimeLong()+"");
	            
	            
	            Iterable<GroupPartake> groupPartakes = groupPartakeService
	                    .findAllByGroupIdOrderByDateTime(goodGroup.getId());
	            
	            
	            List<Map<String, String>> groupMembers = new ArrayList<Map<String, String>>();
	            int i = 0 ;
	            for (GroupPartake groupPartake : groupPartakes) {
	                Map<String, String> groupMember = new HashMap<String, String>();
	                String customerId = groupPartake.getCustomerid();
	                Customer customer = customerService.findOne(customerId);
	                String openid = customer.getOpenId();
	                UserInfo userInfo = wxUserInfoService.findByOpenid(openid);
	                groupMember.put("name", userInfo.getNickname());
	                groupMember.put("headImg", userInfo.getHeadimgurl());
	                groupMember.put("role", groupPartake.getRole() + "");
	                groupMember.put("datetime", mySimpleDateFormat.format(groupPartake
	                        .getDateTime().toDate()));
	                groupMembers.add(groupMember);
	                i++;
	                if(i==7){
	                	break;
	                }
	               
	            }
	            responseGroup.put("members", groupMembers);
	            responseGroups.add(responseGroup);
    		}else{
    			HotGroup hotGroup = hotGroupService.findOneByGroupId(goodGroup.getId());
    			hotGroup.setStatus(1);
    			hotGroupService.update(hotGroup);
    		}

    	}
    	httpServletRequest.setAttribute("groups", responseGroups);
    	return "info/hotGroup";
    }
}
