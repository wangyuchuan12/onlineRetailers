package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.SystemAdGoodHeaderImg;
import com.wyc.repositories.AdGoodHeaderImgRepository;

@Service
public class AdGoodHeaderImgService {
    @Autowired
    private AdGoodHeaderImgRepository adGoodHeaderImgRepository;

    public Iterable<SystemAdGoodHeaderImg> findAllOrderByRankAsc() {
        return adGoodHeaderImgRepository.findAllOrderByRankAsc();
    }
}
