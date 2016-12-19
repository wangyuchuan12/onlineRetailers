package com.wyc.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.Good;
import com.wyc.domain.GoodClickGood;
import com.wyc.domain.GoodView;
import com.wyc.domain.MyResource;
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
	public List<GoodViewVo> findAllGoodViewsByGoodTypeId(String goodTypeId,UserInfo userInfo){
		List<GoodView> goodViews = goodViewRepository.findAllByGoodTypeId(goodTypeId);
		Map<String, Integer> goodMap = new HashMap<>();
		for(GoodView goodView:goodViews){
			if(goodView.getType()==0){
				goodMap.put(goodView.getGoodId(), goodView.getSeq());
			}
		}
		List<GoodViewVo> goodViewVos = new ArrayList<>();
		
		Iterable<Good> goods = goodRepository.findAll(goodMap.keySet());
		
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
			goodViewVo.setSeq(goodMap.get(good.getId()));
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
}
