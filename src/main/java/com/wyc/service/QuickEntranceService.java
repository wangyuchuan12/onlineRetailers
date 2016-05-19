package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.SystemQuickEntrance;
import com.wyc.repositories.QuickEntranceRepository;

@Service
public class QuickEntranceService {
    @Autowired
    private QuickEntranceRepository quickEntranceRepository;

    public Iterable<SystemQuickEntrance> findAllOrderByRankAsc() {
        return quickEntranceRepository.findOrderByRankAsc();
    }
}
