package com.wyc.listener;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wyc.domain.Customer;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.GroupPartakeDeliver;
import com.wyc.domain.GroupPartakeLog;
import com.wyc.domain.OpenGroupCoupon;
import com.wyc.domain.OrderDetail;
import com.wyc.domain.TempGroupOrder;
import com.wyc.service.GroupPartakeLogService;
import com.wyc.service.GroupPartakeService;


@Service
public class PayResultListener {
	
	@Autowired
	private GroupPartakeLogService groupPartakeLogService;
	
	@Autowired
	private GroupPartakeService groupPartakeService;
	
	final static Logger logger = LoggerFactory.getLogger(GroupPartakeLogService.class);

	public void payFailure(TempGroupOrder tempGroupOrder){
		
	}

	public void prepareHandle(TempGroupOrder tempGroupOrder) {
		// TODO Auto-generated method stub
		
	}

	public void paySuccess(Customer customer, GoodGroup goodGroup, GoodOrder goodOrder, OpenGroupCoupon openGroupCoupon,
			GroupPartake groupPartake, GroupPartakeDeliver groupPartakeDeliver, OrderDetail orderDetail) {
		GroupPartakeLog groupPartakeLog = new GroupPartakeLog();
		if(groupPartake.getRole()==1){
			groupPartakeLog.setContent("以<font color='red'>团长</font>的身份开团️");
			groupPartakeLog.setHappenTime(groupPartake.getCreateAt());
			groupPartakeLog.setGroupId(goodGroup.getId());
			groupPartakeLog.setGroupPartakeId(groupPartake.getId());
		}else if(groupPartake.getRole()==2){
			groupPartakeLog.setContent("成功占领<font color='red'>沙发</font>的位置");
			groupPartakeLog.setHappenTime(groupPartake.getCreateAt());
			groupPartakeLog.setGroupId(goodGroup.getId());
			groupPartakeLog.setGroupPartakeId(groupPartake.getId());
		}else if(groupPartake.getRole()==3){
			groupPartakeLog.setContent("参加该团");
			groupPartakeLog.setHappenTime(groupPartake.getCreateAt());
			groupPartakeLog.setGroupId(goodGroup.getId());
			groupPartakeLog.setGroupPartakeId(groupPartake.getId());
		}
		
		groupPartakeLogService.add(groupPartakeLog);
		
		
		
		if(goodGroup.getIsReceiveGoodsTogether()==1){
			if(groupPartake.getRole()==1){
				GroupPartakeLog reliefLog = new GroupPartakeLog();
				reliefLog.setContent("设置统一收货，负责接收所有用户的包裹由团长统一发给大家,优惠了<font color='red'>￥"+groupPartake.getReliefValue()+"</font>");
				reliefLog.setHappenTime(groupPartake.getCreateAt());
				reliefLog.setGroupId(goodGroup.getId());
				reliefLog.setGroupPartakeId(groupPartake.getId());
				groupPartakeLogService.add(reliefLog);
			}else{
				GroupPartakeLog reliefLog = new GroupPartakeLog();
				reliefLog.setContent("团长设置了统一收货，已经验证了团长的手机号，团长负责接收包裹,优惠了<font color='red'>￥"+groupPartake.getReliefValue()+"</font>");
				reliefLog.setHappenTime(groupPartake.getCreateAt());
				reliefLog.setGroupId(goodGroup.getId());
				reliefLog.setGroupPartakeId(groupPartake.getId());
				groupPartakeLogService.add(reliefLog);
				
				GroupPartake insteadPartakeGroupPartake = groupPartakeService.findOne(groupPartake.getInsteadPartakeId());
				BigDecimal waitForRefund = insteadPartakeGroupPartake.getWaitForRefund();
				if(waitForRefund==null){
					waitForRefund = goodGroup.getReceiverInsteadOfRelief();
				}else{
					waitForRefund = waitForRefund.add(goodGroup.getReceiverInsteadOfRelief());
				}
				insteadPartakeGroupPartake.setWaitForRefund(waitForRefund);
				groupPartakeService.update(insteadPartakeGroupPartake);
				
				GroupPartakeLog receiveGoodGroupPartakeLog = new GroupPartakeLog();
				receiveGoodGroupPartakeLog.setGroupPartakeId(groupPartake.getInsteadPartakeId());
				receiveGoodGroupPartakeLog.setGroupId(goodGroup.getId());
				receiveGoodGroupPartakeLog.setHappenTime(new DateTime());
				receiveGoodGroupPartakeLog.setContent("[<font color='red'>"+groupPartake.getNickname()+"</font>]请求将包裹寄放在我这里,"
						+ "我代该用户接收包谷,返利<font color='red'>￥"+goodGroup.getReceiverInsteadOfRelief()+"</font>,"
								+ "目前该订单我拥有￥"+waitForRefund+"返利金额");
				groupPartakeLogService.add(receiveGoodGroupPartakeLog);
			}
		}else if(groupPartake.getIsFindOtherInsteadOfReceiving()==1){
			
			logger.debug("代他人收货设置");
			GroupPartakeLog reliefLog = new GroupPartakeLog();
			reliefLog.setContent("已经指定了包裹接收了，到该接收人那里拿货，优惠了<font color='red'>￥"+groupPartake.getReliefValue()+"</font>");
			reliefLog.setHappenTime(groupPartake.getCreateAt());
			reliefLog.setGroupId(goodGroup.getId());
			reliefLog.setGroupPartakeId(groupPartake.getId());
			groupPartakeLogService.add(groupPartakeLog);
			
			
			
			
			
			GroupPartake insteadPartakeGroupPartake = groupPartakeService.findOne(groupPartake.getInsteadPartakeId());
			BigDecimal waitForRefund = insteadPartakeGroupPartake.getWaitForRefund();
			if(waitForRefund==null){
				waitForRefund = goodGroup.getReceiverInsteadOfRelief();
			}else{
				waitForRefund = waitForRefund.add(goodGroup.getReceiverInsteadOfRelief());
			}
			insteadPartakeGroupPartake.setWaitForRefund(waitForRefund);
			groupPartakeService.update(insteadPartakeGroupPartake);
			
			
			GroupPartakeLog receiveGoodGroupPartakeLog = new GroupPartakeLog();
			receiveGoodGroupPartakeLog.setGroupPartakeId(groupPartake.getInsteadPartakeId());
			receiveGoodGroupPartakeLog.setGroupId(goodGroup.getId());
			receiveGoodGroupPartakeLog.setContent("<font color='red'>"+groupPartake.getNickname()+"</font>请求将包裹寄放在我这里，我代该用户接收包谷,目前该订单我拥有<font color='red'>￥"+waitForRefund+"</font>返利金额");
			groupPartakeLogService.add(receiveGoodGroupPartakeLog);
			
			logger.debug("包裹寄放在我这里");
			
		}else if(groupPartake.getIsInsteadOfReceiving()==1){
			GroupPartakeLog reliefLog = new GroupPartakeLog();
			reliefLog.setContent("设置允许代他人接收包裹"+"优惠￥"+groupPartake.getReliefValue());
			reliefLog.setHappenTime(groupPartake.getCreateAt());
			reliefLog.setGroupId(goodGroup.getId());
			reliefLog.setGroupPartakeId(groupPartake.getId());
			groupPartakeLogService.add(reliefLog);
		}
		
	}
}
