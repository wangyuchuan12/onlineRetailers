package com.wyc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GoodType;
import com.wyc.repositories.GoodTypeRepository;

@Service
public class GoodTypeService {
    @Autowired
    private GoodTypeRepository goodTypeRepository;

    public GoodType findOne(String id) {
        return goodTypeRepository.findOne(id);
    }

    public Iterable<GoodType> findAll() {
        return goodTypeRepository.findAll();
    }

    public List<GoodType> findAllByIsDefault(boolean isDefault) {
        return goodTypeRepository.findAllByIsDefault(isDefault);
    }
}
