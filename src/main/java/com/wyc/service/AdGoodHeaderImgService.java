package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.AdGoodHeaderImg;
import com.wyc.repositories.AdGoodHeaderImgRepository;

@Service
public class AdGoodHeaderImgService {
    @Autowired
    private AdGoodHeaderImgRepository adGoodHeaderImgRepository;

    public Iterable<AdGoodHeaderImg> findAllByGoodTypeId(String goodTypeId) {
        return adGoodHeaderImgRepository.findAllByGoodTypeId(goodTypeId);
    }
}
