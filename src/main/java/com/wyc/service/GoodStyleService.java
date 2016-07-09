package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GoodStyle;
import com.wyc.repositories.GoodStyleRepository;

@Service
public class GoodStyleService {
    @Autowired
    private GoodStyleRepository goodStyleRepository;

    public Iterable<GoodStyle> findAllByGoodId(String goodId) {
        return goodStyleRepository.findAllByGoodId(goodId);
    }

    public GoodStyle findOne(String id) {
        return goodStyleRepository.findOne(id);
    }
}
