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
		
		
	}
}
