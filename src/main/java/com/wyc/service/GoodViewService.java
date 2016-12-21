package com.wyc.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wyc.defineBean.MySimpleDateFormat;
import com.wyc.domain.Customer;
import com.wyc.domain.Good;
import com.wyc.domain.GoodClickGood;
import com.wyc.domain.GoodGroup;
import com.wyc.domain.GoodOrder;
import com.wyc.domain.GoodView;
import com.wyc.domain.GroupPartake;
import com.wyc.domain.HotGroup;
import com.wyc.domain.MyResource;
import com.wyc.domain.OrderDetail;
import com.wyc.domain.response.vo.GoodViewVo;
import com.wyc.repositories.GoodRepository;
import com.wyc.repositories.GoodViewRepository;
import com.wyc.wx.domain.UserInfo;


@Service
public class GoodViewService {
	
	@Autowired
	private GoodViewRepository goodViewRepository;
	
	@Autowired
	private GoodClickGoodService goodClickGoodService;
	@Autowired
	private GoodRepository goodRepository;
	
	@Autowired
	private MyResourceService resourceService;
	
	@Autowired
	private HotGroupService hotGroupService;
	
	@Autowired
	private GoodService goodService;
	
	@Autowired
	private GroupPartakeService groupPartakeService;
	
	@Autowired
	private GoodGroupService goodGroupService;
	
	@Autowired
	private MySimpleDateFormat mySimpleDateFormat;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private WxUserInfoService wxUserInfoService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	

	
	private GoodOrderService goodOrderService;
	private GoodGroup checkTimeout(GoodGroup goodGroup){
        if(goodGroup.getResult()==1){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(goodGroup.getStartTime().toDate());
            calendar.add(Calendar.HOUR, goodGroup.getTimeLong());
            if(calendar.getTime().getTime()<new Date().getTime()){
                goodGroup.setResult(0);
                goodGroup = goodGroupService.save(goodGroup);
                OrderDetail orderDetail = orderDetailService.findByGruopId(goodGroup.getId());
                GoodOrder goodOrder = goodOrderService.findOne(orderDetail.getOrderId());
                goodOrder.setStatus(5);
                goodOrderService.save(goodOrder);
            }
        }
        return goodGroup;
    }
	public List<GoodViewVo> findAllGoodViewsByGoodTypeId(String goodTypeId,UserInfo userInfo){
		List<GoodView> goodViews = goodViewRepository.findAllByGoodTypeIdAndIsDel(goodTypeId,0);
		Map<String, GoodView> goodMap = new HashMap<>();
		Map<String, GoodView> groupMap = new HashMap<>();
		for(GoodView goodView:goodViews){
			if(goodView.getType()==0){
				goodMap.put(goodView.getGoodId(), goodView);
			}else if(goodView.getType()==1){
				groupMap.put(goodView.getHotGroupId(), goodView);
			}
		}
		
		System.out.println("goodMapSize:"+goodMap.size());
		System.out.println("groupMapSize:"+groupMap.size());
		List<GoodViewVo> goodViewVos = new ArrayList<>();
		
		Iterable<Good> goods = goodRepository.findAll(goodMap.keySet());
		
		Iterable<HotGroup> hotGroups = hotGroupService.findAll(groupMap.keySet());
		
		for(HotGroup hotGroup:hotGroups){
				GoodGroup goodGroup = goodGroupService.findOne(hotGroup.getGroupId());
				checkTimeout(goodGroup);
				if(goodGroup.getResult()==1){	
		    		GoodViewVo goodViewVo = new GoodViewVo();
		    		goodViewVo.setType(1);
		    		Good good = goodService.findOne(goodGroup.getGoodId());
		    		goodViewVo.setGroup_id(goodGroup.getId());
		    		goodViewVo.setResult(goodGroup.getResult());
		    		goodViewVo.setName(good.getName());
		 
		    		goodViewVo.setHead_img(resourceService.findOne(good.getHeadImg()).getUrl());
		    		
		    		goodViewVo.setTotal_price(goodGroup.getTotalPrice());
		    		goodViewVo.setAdminId(good.getAdminId());
		    		goodViewVo.setGroup_num(goodGroup.getNum());
		    		
		    		goodViewVo.setPartake_num(groupPartakeService.countByGroupId(goodGroup.getId()));
		    		goodViewVo.setStartTime( mySimpleDateFormat.format(goodGroup.getStartTime().toDate()));
	
		    		
		    		goodViewVo.setTimeLong(goodGroup.getTimeLong());
		            
		            Iterable<GroupPartake> groupPartakes = groupPartakeService
		                    .findAllByGroupIdOrderByDateTime(goodGroup.getId());
		            
		            
		            List<Map<String, String>> groupMembers = new ArrayList<Map<String, String>>();
		            int i = 0 ;
		            for (GroupPartake groupPartake : groupPartakes) {
		                Map<String, String> groupMember = new HashMap<String, String>();
		                String customerId = groupPartake.getCustomerid();
		                Customer customer = customerService.findOne(customerId);
		                String openid = customer.getOpenId();
		                UserInfo groupPartakeUserInfo = wxUserInfoService.findByOpenid(openid);
		                groupMember.put("name", groupPartakeUserInfo.getNickname());
		                groupMember.put("headImg", groupPartakeUserInfo.getHeadimgurl());
		                groupMember.put("role", groupPartake.getRole() + "");
		                groupMember.put("datetime", mySimpleDateFormat.format(groupPartake
		                        .getDateTime().toDate()));
		                groupMembers.add(groupMember);
		                i++;
		                if(i==7){
		                	break;
		                }
		            }
		            goodViewVo.setMembers(groupMembers);
		            goodViewVos.add(goodViewVo);
				}else{
					GoodView goodView = groupMap.get(hotGroup.getId());
					goodView.setIsDel(1);
					this.update(goodView);
					
				}
		}
		for(Good good:goods){
			GoodViewVo goodViewVo = new GoodViewVo();
			goodViewVo.setId(good.getId());
			goodViewVo.setInstruction(good.getInstruction());
			goodViewVo.setName(good.getName());
			goodViewVo.setAlone_discount(good.getAloneDiscount());
			goodViewVo.setAlone_original_cost(good.getAloneOriginalCost());
			goodViewVo.setFlow_price(good.getFlowPrice());
			goodViewVo.setGroup_discount(good.getGroupDiscount());
			goodViewVo.setGroup_num(good.getGroupNum());
			goodViewVo.setGroup_original_cost(good.getGroupOriginalCost());
			goodViewVo.setMarket_price(good.getMarketPrice());
			goodViewVo.setCoupon_cost(good.getCouponCost());
			goodViewVo.setTitle(good.getTitle());
			goodViewVo.setNotice(good.getNotice());
			goodViewVo.setStock(good.getStock());
			goodViewVo.setSalesVolume(good.getSalesVolume());
			goodViewVo.setAdminId(good.getAdminId());
			goodViewVo.setSeq(goodMap.get(good.getId()).getSeq());
			goodViewVo.setType(0);
			int goodClickCount = goodClickGoodService.countByGoodId(good.getId());
			goodViewVo.setGoodClickCount(goodClickCount);
			GoodClickGood goodClickGood = goodClickGoodService.findByOpenidAndGoodId(userInfo.getOpenid(),good.getId());
		    if(goodClickGood==null){
		    	goodViewVo.setIsGoodClick(false);
		    }else{
		    	goodViewVo.setIsGoodClick(true);
		    }
			    MyResource myResource = resourceService.findOne(good.getHeadImg());
			    if(myResource!=null){
			    	goodViewVo.setHead_img(myResource.getUrl());
			    }
			    System.out.println("add.........");
			    goodViewVos.add(goodViewVo);
			}
		Collections.sort(goodViewVos);
		return goodViewVos;
	}
	
	public void update(GoodView goodView) {
		goodView.setUpdateAt(new DateTime());
		goodViewRepository.save(goodView);
		
	}
}
