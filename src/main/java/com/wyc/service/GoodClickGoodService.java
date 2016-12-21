package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GoodClickGood;
import com.wyc.repositories.GoodClickGoodRepository;

@Service
public class GoodClickGoodService {
	@Autowired
	private GoodClickGoodRepository goodClickGoodRepository;

	public GoodClickGood findByOpenid(String openId) {
		return goodClickGoodRepository.findByOpenid(openId);
	}

	public GoodClickGood add(GoodClickGood goodClickGood) {
		goodClickGood.setUpdateAt(new DateTime());
		goodClickGood.setCreateAt(new DateTime());
		goodClickGood.setId(UUID.randomUUID().toString());
		return goodClickGoodRepository.save(goodClickGood);
		
	}

	public int countByGoodId(String goodId) {
		return goodClickGoodRepository.countByGoodId(goodId);
	}

	public GoodClickGood findByOpenidAndGoodId(String openid, String goodId) {
		return goodClickGoodRepository.findByOpenidAndGoodId(openid,goodId);
	}
}
