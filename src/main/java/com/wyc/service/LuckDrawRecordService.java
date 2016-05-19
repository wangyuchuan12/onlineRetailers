package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.LuckDrawRecord;
import com.wyc.repositories.LuckDrawRecordRepository;

@Service
public class LuckDrawRecordService {
    @Autowired
    private LuckDrawRecordRepository luckDrawRecordRepository;
    public LuckDrawRecord add(LuckDrawRecord luckDrawRecord) {
        luckDrawRecord.setCreateAt(new DateTime());
        luckDrawRecord.setUpdateAt(new DateTime());
        luckDrawRecord.setId(UUID.randomUUID().toString());
        return luckDrawRecordRepository.save(luckDrawRecord);
    }
    public LuckDrawRecord findOne(String luckDrawRecordId) {
        return luckDrawRecordRepository.findOne(luckDrawRecordId);
    }

}
