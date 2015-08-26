package com.wyc.controller.api;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.OrderDetail;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.OrderDetailService;
import com.wyc.wx.domain.UserInfo;

@RestController
public class GoodsApi {
    @Autowired
    private GoodService goodService;
    @Autowired
    private GoodOrderService goodOrderService;
    @Autowired
    private GoodGroupService goodGroupService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private GroupPartakeService groupPartakeService;
    
    final static Logger logger = LoggerFactory.getLogger(GoodsApi.class);
    @RequestMapping(value = "/api/pay_success")
    @UserInfoFromWebAnnotation
    @Transactional
    public Object paySouccess(HttpServletRequest httpServletRequest)throws Exception{
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest) httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        String good_id = httpServletRequest.getParameter("good_id");
        String payType = httpServletRequest.getParameter("pay_type");
        String status = httpServletRequest.getParameter("status");
        //只有当状态为未付款或者已付款未发货才能生成订单
        if (status.equals("1") || status.equals("2")) {
            Good good = goodService.findOne(good_id);
            float cost = 0;
            //购买方式，0表示团购，1表示单买，2表示开团劵购买
            if (payType.equals("0")) {
                cost = good.getFlowPrice() + good.getGroupDiscount()
                        * good.getGroupOriginalCost();
            } else if (payType.equals("1")) {
                cost = good.getFlowPrice() + good.getAloneDiscount()
                        * good.getAloneOriginalCost();
            } else if (payType.equals("2")) {
                cost = good.getFlowPrice();
            }
            GoodOrder goodOrder = new GoodOrder();
            goodOrder.setGoodId(good.getId());
            goodOrder.setGoodPrice(good.getFlowPrice());
            goodOrder.setCost(cost);
            goodOrder.setCreateTime(new DateTime());
            goodOrder.setFlowPrice(good.getFlowPrice());
            goodOrder.setStatus(Integer.parseInt(status));
            goodOrder = goodOrderService.add(goodOrder);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setGoodId(good.getId());
            orderDetail.setNum(good.getGroupNum());
            orderDetail.setOrderId(goodOrder.getId());
            orderDetail.setStatus(Integer.parseInt(status));
            orderDetail.setCustomerId(customerService.findByOpenId(userInfo.getOpenid()).getId());
            //只有当状态为成功购买并且购买方式为团购或者开团劵购买才能生成团记录
            if (status.equals("2")&&(payType.equals("0")||payType.equals("2"))) {
                GoodGroup goodGroup = new GoodGroup();
                goodGroup.setGoodId(good.getId());
                goodGroup.setNum(good.getGroupNum());
                goodGroup.setGroupHead(userInfo.getId());
                goodGroup.setResult(1);
                goodGroup.setStartTime(new DateTime());
                goodGroup.setTimeLong(24);
                goodGroup.setTotalPrice(cost);
                goodGroup = goodGroupService.add(goodGroup);
                orderDetail.setGroupId(goodGroup.getId());
                GroupPartake groupPartake = new GroupPartake();
                
                logger.debug("get customer by openid {}"+userInfo.getOpenid());
                Customer customer = customerService.findByOpenId(userInfo.getOpenid());
                groupPartake.setCustomerid(customer.getId());
                groupPartake.setDateTime(new DateTime());
                groupPartake.setGroupId(goodGroup.getId());
                groupPartake.setRole(1);
                groupPartake.setType(Integer.parseInt(payType));
                groupPartakeService.add(groupPartake);
            }
            orderDetailService.add(orderDetail);
            return goodOrder;
        } else {
            return null;
        }

    }
}
