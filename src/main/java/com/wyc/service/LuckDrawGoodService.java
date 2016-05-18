package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.LuckDrawGood;
import com.wyc.repositories.LuckDrawGoodRepository;

@Service
public class LuckDrawGoodService {
    @Autowired
    private LuckDrawGoodRepository luckDrawGoodRepository;
    public Iterable<LuckDrawGood> findAll() {
        return luckDrawGoodRepository.findAll();
    }

}
