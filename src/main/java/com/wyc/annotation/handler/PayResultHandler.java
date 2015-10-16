package com.wyc.annotation.handler;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.OpenGroupCoupon;
import com.wyc.domain.OrderDetail;
import com.wyc.domain.TemporaryData;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.OpenGroupCouponService;
import com.wyc.service.OrderDetailService;
import com.wyc.service.TemporaryDataService;
public class PayResultHandler implements Handler{
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
    @Autowired
    private OpenGroupCouponService openGroupCouponService;
    @Autowired
    private TemporaryDataService temporaryDataService;
    final static Logger logger = LoggerFactory.getLogger(PayResultHandler.class);
    @Override
    @Transactional
    public Object handle(HttpServletRequest httpServletRequest)
            throws Exception {
        String good_id = httpServletRequest.getAttribute("good_id").toString();
        String payType = httpServletRequest.getAttribute("pay_type").toString();
        String status = httpServletRequest.getAttribute("status").toString();
        String openid = httpServletRequest.getAttribute("openId").toString();
        String userId = httpServletRequest.getAttribute("userId").toString();
        String outTradeNo = null;
        if(httpServletRequest.getAttribute("outTradeNo")!=null){
            outTradeNo = httpServletRequest.getAttribute("outTradeNo").toString();
        }
        
        TemporaryData nowpageTemporaryData = temporaryDataService.findByMyKeyAndName(openid, "nowpage");
        TemporaryData frompageTemporaryData = temporaryDataService.findByMyKeyAndName(openid, "frompage");
        if(!(frompageTemporaryData!=null&nowpageTemporaryData.getValue().equals("2")&&frompageTemporaryData.getValue().equals("4"))){
            String address = null;
            if(httpServletRequest.getAttribute("address")!=null){
                address = httpServletRequest.getAttribute("address").toString();
            }
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
                goodOrder.setAddress(address);
                goodOrder.setType(Integer.parseInt(payType));
                goodOrder = goodOrderService.add(goodOrder);
                httpServletRequest.setAttribute("orderId", goodOrder.getId());
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setGoodId(good.getId());
                orderDetail.setNum(good.getGroupNum());
                orderDetail.setOrderId(goodOrder.getId());
                orderDetail.setStatus(Integer.parseInt(status));
                orderDetail.setCustomerId(customerService.findByOpenId(openid).getId());
                GroupPartake groupPartake = new GroupPartake();
                Customer customer = customerService.findByOpenId(openid);
                //只有当状态为成功购买并且购买方式为团购或者开团劵购买才能生成团记录
                if (status.equals("2")&&(payType.equals("0")||payType.equals("2"))) {
                    GoodGroup goodGroup = new GoodGroup();
                    goodGroup.setGoodId(good.getId());
                    goodGroup.setNum(good.getGroupNum());
                    goodGroup.setGroupHead(userId);
                    goodGroup.setResult(1);
                    goodGroup.setStartTime(new DateTime());
                    goodGroup.setTimeLong(24);
                    goodGroup.setTotalPrice(cost);
                    
                    
                    goodGroup = goodGroupService.add(goodGroup);
                    
                    TemporaryData groupIdTemporaryData = new TemporaryData();
                    groupIdTemporaryData.setMykey(outTradeNo);
                    groupIdTemporaryData.setName("groupId");
                    groupIdTemporaryData.setValue(goodGroup.getId());
                    TemporaryData lastGroupId = temporaryDataService.findByMyKeyAndName(openid,"lastGroupId");
                    if(lastGroupId==null){
                        lastGroupId = new TemporaryData();
                        lastGroupId.setMykey(openid);
                        lastGroupId.setName("lastGroupId");
                        lastGroupId.setValue(goodGroup.getId());
                        temporaryDataService.add(lastGroupId);
                    }else{
                        lastGroupId.setValue(goodGroup.getId());
                        temporaryDataService.save(lastGroupId);
                    }
                    
                    TemporaryData groupId = temporaryDataService.findByMyKeyAndName(outTradeNo, "groupId");
                    if(groupId==null){
                        groupId = new TemporaryData();
                        groupId.setMykey(outTradeNo);
                        groupId.setName("groupId");
                        groupId.setValue(goodGroup.getId());
                        temporaryDataService.add(groupId);
                    }else{
                        groupId.setValue(goodGroup.getId());
                        temporaryDataService.save(groupId);
                    }
                    httpServletRequest.setAttribute("groupId", goodGroup.getId());
                    orderDetail.setGroupId(goodGroup.getId());
                    logger.debug("get customer by openid {}"+openid);
                    groupPartake.setGroupId(goodGroup.getId());
                    if(payType.equals("2")){
                        OpenGroupCoupon openGroupCoupon = openGroupCouponService.getFirstRecord(customer.getId(), good.getId(), new DateTime(),1);
                        if(openGroupCoupon==null){
                            return new RuntimeException("there is no available openGroupCoupon");
                        }
                        openGroupCoupon.setStatus(0);
                        openGroupCouponService.save(openGroupCoupon);
                    }
                    
                }
                groupPartake.setOrderId(goodOrder.getId());
               
                groupPartake.setCustomerid(customer.getId());
                groupPartake.setDateTime(new DateTime());
                groupPartake.setRole(1);
                groupPartake.setType(Integer.parseInt(payType));
                groupPartakeService.add(groupPartake);
                orderDetailService.add(orderDetail);
                
                TemporaryData orderIdTemporaryData = temporaryDataService.findByMyKeyAndName(outTradeNo, "orderId");
                if(orderIdTemporaryData==null){
                    orderIdTemporaryData = new TemporaryData();
                    orderIdTemporaryData.setMykey(outTradeNo);
                    orderIdTemporaryData.setName("orderId");
                    orderIdTemporaryData.setValue(goodOrder.getId());
                    temporaryDataService.add(orderIdTemporaryData);
                }else{
                    orderIdTemporaryData.setValue(goodOrder.getId());
                    temporaryDataService.save(orderIdTemporaryData);
                }
                
                
               
                return goodOrder;
            } else {
                return null;
            }
        }else{
            TemporaryData groupId = temporaryDataService.findByMyKeyAndName(openid, "nowgroup");
            GoodGroup goodGroup = goodGroupService.findOne(groupId.getValue());
            Customer customer = customerService.findByOpenId(openid);
            GroupPartake groupPartake = new GroupPartake();
            groupPartake.setCustomerid(customer.getId());
            groupPartake.setDateTime(new DateTime());
            groupPartake.setRole(3);
            groupPartake.setType(0);
            groupPartake.setGroupId(groupId.getValue());
            groupPartakeService.add(groupPartake);
            
            int groupCount = groupPartakeService.countByGroupId(goodGroup.getId());
            if(groupCount==goodGroup.getNum()){
                goodGroup.setResult(2);
                goodGroupService.save(goodGroup);
            }
            TemporaryData lastGroupId = temporaryDataService.findByMyKeyAndName(openid,"lastGroupId");
            if(lastGroupId==null){
                lastGroupId = new TemporaryData();
                lastGroupId.setMykey(openid);
                lastGroupId.setName("lastGroupId");
                lastGroupId.setValue(goodGroup.getId());
                temporaryDataService.add(lastGroupId);
            }else{
                lastGroupId.setValue(goodGroup.getId());
                temporaryDataService.save(lastGroupId);
            }
            return null;
        }
        
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
