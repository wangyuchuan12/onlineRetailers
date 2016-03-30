package com.wyc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.SystemGoodType;
import com.wyc.repositories.GoodTypeRepository;

@Service
public class GoodTypeService {
    @Autowired
    private GoodTypeRepository goodTypeRepository;

    public SystemGoodType findOne(String id) {
        return goodTypeRepository.findOne(id);
    }

    public Iterable<SystemGoodType> findAll() {
        return goodTypeRepository.findAll();
    }

    public List<SystemGoodType> findAllByIsDefault(boolean isDefault) {
        return goodTypeRepository.findAllByIsDefault(isDefault);
    }
}
