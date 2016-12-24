package com.wyc.listener;

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


@Service
public class PayResultListener {
	
	@Autowired
	private GroupPartakeLogService groupPartakeLogService;

	public void payFailure(TempGroupOrder tempGroupOrder){
		
	}

	public void prepareHandle(TempGroupOrder tempGroupOrder) {
		// TODO Auto-generated method stub
		
	}

	public void paySuccess(Customer customer, GoodGroup goodGroup, GoodOrder goodOrder, OpenGroupCoupon openGroupCoupon,
			GroupPartake groupPartake, GroupPartakeDeliver groupPartakeDeliver, OrderDetail orderDetail) {
		GroupPartakeLog groupPartakeLog = new GroupPartakeLog();
		if(groupPartake.getRole()==1){
			groupPartakeLog.setContent("以团长的身份组团️");
			groupPartakeLog.setHappenTime(groupPartake.getCreateAt());
			groupPartakeLog.setGroupId(goodGroup.getId());
			groupPartakeLog.setGroupPartakeId(groupPartake.getId());
		}else if(groupPartake.getRole()==1){
			groupPartakeLog.setContent("成功占领沙发的位置");
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
		
		
		GroupPartakeLog reliefLog = new GroupPartakeLog();
		if(goodGroup.getIsReceiveGoodsTogether()==1){
			if(groupPartake.getRole()==0){
				reliefLog.setContent("设置统一收货，负责接收所有用户的包裹由团长统一发给大家,优惠了￥"+groupPartake.getReliefValue());
				reliefLog.setHappenTime(groupPartake.getCreateAt());
				reliefLog.setGroupId(goodGroup.getId());
				reliefLog.setGroupPartakeId(groupPartake.getId());
			}else{
				reliefLog.setContent("团长设置了统一收货，已经验证了团长的手机号，团长负责接收包裹,优惠了￥"+groupPartake.getReliefValue());
				reliefLog.setHappenTime(groupPartake.getCreateAt());
				reliefLog.setGroupId(goodGroup.getId());
				reliefLog.setGroupPartakeId(groupPartake.getId());
			}
		}else if(groupPartake.getIsFindOtherInsteadOfReceiving()==1){
			reliefLog.setContent("已经指定了包裹接收了，到该接收人那里拿货，优惠了￥"+groupPartake.getReliefValue());
			reliefLog.setHappenTime(groupPartake.getCreateAt());
			reliefLog.setGroupId(goodGroup.getId());
			reliefLog.setGroupPartakeId(groupPartake.getId());
			
			GroupPartakeLog receiveGoodGroupPartakeLog = new GroupPartakeLog();
			receiveGoodGroupPartakeLog.setGroupPartakeId(groupPartake.getInsteadPartakeId());
			receiveGoodGroupPartakeLog.setGroupId(goodGroup.getId());
			receiveGoodGroupPartakeLog.setContent(groupPartake.getNickname()+"请求将包裹寄放在我这里，我代该用户接收包谷");
			groupPartakeLogService.add(receiveGoodGroupPartakeLog);
		}else if(groupPartake.getIsInsteadOfReceiving()==1){
			reliefLog.setContent("设置待他人接收包裹");
			reliefLog.setHappenTime(groupPartake.getCreateAt());
			reliefLog.setGroupId(goodGroup.getId());
			reliefLog.setGroupPartakeId(groupPartake.getId());
		}
		groupPartakeLogService.add(reliefLog);
	}
}
