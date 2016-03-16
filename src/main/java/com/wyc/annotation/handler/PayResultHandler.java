package com.wyc.annotation.handler;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.domain.Customer;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.GroupPartakeDeliver;
import com.wyc.domain.GroupPartakePayment;
import com.wyc.domain.OpenGroupCoupon;
import com.wyc.domain.OrderDetail;
import com.wyc.domain.TempGroupOrder;
import com.wyc.domain.TemporaryData;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodGroupService;
import com.wyc.service.GoodOrderService;
import com.wyc.service.GoodService;
import com.wyc.service.GroupPartakeDeliverService;
import com.wyc.service.GroupPartakePaymentService;
import com.wyc.service.GroupPartakeService;
import com.wyc.service.OpenGroupCouponService;
import com.wyc.service.OrderDetailService;
import com.wyc.service.TempGroupOrderService;
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
    @Autowired
    private TempGroupOrderService tempGroupOrderService;
    @Autowired
    private GroupPartakeDeliverService groupPartakeDeliverService;
    @Autowired
    private GroupPartakePaymentService groupPartakePaymentService;
    final static Logger logger = LoggerFactory.getLogger(PayResultHandler.class);
    @Override
    @Transactional
    public Object handle(HttpServletRequest httpServletRequest)
            throws Exception {
    	
        String outTradeNo = httpServletRequest.getAttribute("outTradeNo").toString();
        TempGroupOrder tempGroupOrder = tempGroupOrderService.findByOutTradeNo(outTradeNo);
        Customer customer = null;
        if(tempGroupOrder!=null){
            customer = customerService.findByOpenId(tempGroupOrder.getOpenid());
        }
        if(tempGroupOrder!=null&&(tempGroupOrder.getGoodOrderType()==0||tempGroupOrder.getGoodOrderType()==2)){
            String openid = tempGroupOrder.getOpenid();
            GoodOrder goodOrder = new GoodOrder();
            goodOrder.setAddress(tempGroupOrder.getAddress());
            goodOrder.setAddressId(tempGroupOrder.getAddressId());
            goodOrder.setCode(tempGroupOrder.getCode());
            goodOrder.setCost(tempGroupOrder.getCost());
            goodOrder.setDeliveryTime(new DateTime());
            goodOrder.setFlowPrice(tempGroupOrder.getFlowPrice());
            goodOrder.setGoodId(tempGroupOrder.getGoodId());
            goodOrder.setGoodPrice(tempGroupOrder.getGoodPrice());
            goodOrder.setPaymentTime(new DateTime());
            goodOrder.setStatus(2);
            goodOrder.setType(0);
            goodOrder.setCreateTime(new DateTime());
            goodOrder = goodOrderService.add(goodOrder);
            
            
            GoodGroup goodGroup = new GoodGroup();
            goodGroup.setGoodId(tempGroupOrder.getGoodId());
            goodGroup.setGroupHead(customer.getId());
            goodGroup.setNum(tempGroupOrder.getNum());
            goodGroup.setResult(1);
            goodGroup.setStartTime(new DateTime());
            goodGroup.setTimeLong(24);
            goodGroup.setTotalPrice(tempGroupOrder.getCost());
            goodGroup = goodGroupService.add(goodGroup);
            
            GroupPartake groupPartake = new GroupPartake();
            groupPartake.setCustomerAddress(tempGroupOrder.getCustomerAddress());
            groupPartake.setCustomerid(customer.getId());
            groupPartake.setDateTime(new DateTime());
            groupPartake.setGroupId(goodGroup.getId());
            groupPartake.setOrderId(goodOrder.getId());
            groupPartake.setRole(1);
            groupPartake.setType(0);
            groupPartake = groupPartakeService.add(groupPartake);
            
            GroupPartakeDeliver groupPartakeDeliver = new GroupPartakeDeliver();
            groupPartakeDeliver.setGroupPartakeId(groupPartake.getId());
            groupPartakeDeliverService.add(groupPartakeDeliver);
            GroupPartakePayment groupPartakePayment = new GroupPartakePayment();
            groupPartakePayment.setGroupPartakeId(groupPartake.getId());
            groupPartakePayment.setStatus(1);
            groupPartakePaymentService.add(groupPartakePayment);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setCustomerId(customer.getId());
            orderDetail.setGoodId(tempGroupOrder.getGoodId());
            orderDetail.setGroupId(goodGroup.getId());
            orderDetail.setNum(tempGroupOrder.getNum());
            orderDetail.setOrderId(goodOrder.getId());
            orderDetail.setOutTradeNo(outTradeNo);
            orderDetail.setStatus(2);
            orderDetail = orderDetailService.add(orderDetail);
            
            
            OpenGroupCoupon openGroupCoupon = openGroupCouponService.getFirstRecord(customer.getId(), tempGroupOrder.getGoodId(), new DateTime(), 1);
            if(openGroupCoupon!=null){
            	openGroupCoupon.setStatus(0);
            	openGroupCouponService.save(openGroupCoupon);
            }
            TemporaryData temporaryData = temporaryDataService.findByMyKeyAndName(openid, "lastGroupId");
            if(temporaryData==null){
                temporaryData = new TemporaryData();
                temporaryData.setMykey(openid);
                temporaryData.setName("lastGroupId");
                temporaryData.setValue(goodGroup.getId());
                temporaryDataService.add(temporaryData);
            }else{
                temporaryData.setMykey(openid);
                temporaryData.setName("lastGroupId");
                temporaryData.setValue(goodGroup.getId());
                temporaryDataService.save(temporaryData);
            }
        
        }else if (tempGroupOrder!=null&&tempGroupOrder.getGoodOrderType()==3) {
            String groupId = tempGroupOrder.getGroupId();
            String openid = tempGroupOrder.getOpenid();
            int partNum = groupPartakeService.countByGroupId(groupId);
            GoodGroup goodGroup = goodGroupService.findOne(groupId);
            int groupNum = goodGroup.getNum();
            if(goodGroup!=null){
                GroupPartake groupPartake = new GroupPartake();
                groupPartake.setCustomerAddress(tempGroupOrder.getCustomerAddress());
                groupPartake.setCustomerid(customer.getId());
                groupPartake.setDateTime(new DateTime());
                groupPartake.setGroupId(goodGroup.getId());
                if(partNum==1){
                    groupPartake.setRole(2);
                }else{
                    groupPartake.setRole(3);
                }
                if(partNum+1==groupNum){
                   goodGroup.setResult(2);
                   goodGroupService.save(goodGroup);
                }
                groupPartake.setType(0);
                groupPartake = groupPartakeService.add(groupPartake);
                
                
                GroupPartakeDeliver groupPartakeDeliver = new GroupPartakeDeliver();
                groupPartakeDeliver.setGroupPartakeId(groupPartake.getId());
                groupPartakeDeliverService.add(groupPartakeDeliver);
                GroupPartakePayment groupPartakePayment = new GroupPartakePayment();
                groupPartakePayment.setGroupPartakeId(groupPartake.getId());
                groupPartakePayment.setStatus(1);
                groupPartakePaymentService.add(groupPartakePayment);
                
                TemporaryData temporaryData = temporaryDataService.findByMyKeyAndName(openid, "lastGroupId");
                if(temporaryData==null){
                    temporaryData = new TemporaryData();
                    temporaryData.setMykey(openid);
                    temporaryData.setName("lastGroupId");
                    temporaryData.setValue(goodGroup.getId());
                    temporaryDataService.add(temporaryData);
                }else{
                    temporaryData.setMykey(openid);
                    temporaryData.setName("lastGroupId");
                    temporaryData.setValue(goodGroup.getId());
                    temporaryDataService.save(temporaryData);
                }
            }
        }else if (tempGroupOrder!=null&&tempGroupOrder.getGoodOrderType()==1) {
            GoodOrder goodOrder = new GoodOrder();
            goodOrder.setAddress(tempGroupOrder.getAddress());
            goodOrder.setAddressId(tempGroupOrder.getAddressId());
            goodOrder.setCode(tempGroupOrder.getCode());
            goodOrder.setCost(tempGroupOrder.getCost());
            goodOrder.setCreateTime(new DateTime());
            goodOrder.setFlowPrice(tempGroupOrder.getFlowPrice());
            goodOrder.setGoodId(tempGroupOrder.getGoodId());
            goodOrder.setGoodPrice(tempGroupOrder.getGoodPrice());
            goodOrder.setStatus(2);
            goodOrder.setType(tempGroupOrder.getGoodOrderType());
            goodOrder = goodOrderService.add(goodOrder);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setCustomerId(customer.getId());
            orderDetail.setGoodId(tempGroupOrder.getGoodId());
            orderDetail.setNum(tempGroupOrder.getNum());
            orderDetail.setOrderId(goodOrder.getId());
            orderDetail.setOutTradeNo(tempGroupOrder.getOutTradeNo());
            orderDetail.setStatus(1);
            orderDetail = orderDetailService.add(orderDetail);
            
            GroupPartake groupPartake = new GroupPartake();
            groupPartake.setCustomerAddress(tempGroupOrder.getCustomerAddress());
            groupPartake.setCustomerid(customer.getId());
            groupPartake.setDateTime(new DateTime());
            groupPartake.setOrderId(goodOrder.getId());
            groupPartake.setType(1);
            groupPartakeService.add(groupPartake);
            
            
            GroupPartakeDeliver groupPartakeDeliver = new GroupPartakeDeliver();
            groupPartakeDeliver.setGroupPartakeId(groupPartake.getId());
            groupPartakeDeliverService.add(groupPartakeDeliver);
            GroupPartakePayment groupPartakePayment = new GroupPartakePayment();
            groupPartakePayment.setGroupPartakeId(groupPartake.getId());
            groupPartakePayment.setStatus(1);
            groupPartakePaymentService.add(groupPartakePayment);
            
            TemporaryData temporaryData = temporaryDataService.findByMyKeyAndName(tempGroupOrder.getOpenid(), "lastOrder");
            if(temporaryData!=null){
                temporaryData.setMykey(tempGroupOrder.getOpenid());
                temporaryData.setName("lastOrder");
                temporaryDataService.save(temporaryData);
            }else{
                temporaryData = new TemporaryData();
                temporaryData.setMykey(tempGroupOrder.getOpenid());
                temporaryData.setName("lastOrder");
                temporaryData.setValue(goodOrder.getId());
                temporaryDataService.add(temporaryData);
            }
            
        }
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
