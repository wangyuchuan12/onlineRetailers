package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.QuickEntrance;
import com.wyc.repositories.QuickEntranceRepository;

@Service
public class QuickEntranceService {
    @Autowired
    private QuickEntranceRepository quickEntranceRepository;

    public Iterable<QuickEntrance> findAllOrderByRankAsc() {
        return quickEntranceRepository.findOrderByRankAsc();
    }
}
