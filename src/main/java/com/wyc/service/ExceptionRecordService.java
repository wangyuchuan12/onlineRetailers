package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.ExceptionRecord;
import com.wyc.repositories.ExceptionRecordRepository;

@Service
public class ExceptionRecordService {
    @Autowired
    private ExceptionRecordRepository exceptionRecordRepository;
    public ExceptionRecord add(ExceptionRecord exceptionRecord) {
        exceptionRecord.setId(UUID.randomUUID().toString());
        exceptionRecord.setCreateAt(new DateTime());
        exceptionRecord.setUpdateAt(new DateTime());
        return exceptionRecordRepository.save(exceptionRecord);
        
    }

}
