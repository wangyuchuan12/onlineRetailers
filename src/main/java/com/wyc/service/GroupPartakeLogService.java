package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.GroupPartakeLog;
import com.wyc.repositories.GroupPartakeLogRepository;

@Service
public class GroupPartakeLogService {
	
	@Autowired
	private GroupPartakeLogRepository groupPartakeLogRepository;


	public Iterable<GroupPartakeLog> findAllByGroupIdOrderBySeqAsc(String id) {
		return groupPartakeLogRepository.findAllByGroupIdOrderBySeqAsc(id);
	}


	public void add(GroupPartakeLog groupPartakeLog) {
		groupPartakeLog.setUpdateAt(new DateTime());
		groupPartakeLog.setCreateAt(new DateTime());
		
		groupPartakeLog.setId(UUID.randomUUID().toString());
		groupPartakeLogRepository.save(groupPartakeLog);
	}
}
