package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.HotGroup;
import com.wyc.repositories.HotGroupRepository;

@Service
public class HotGroupService {
	@Autowired
	private HotGroupRepository hotGroupRepository;

	public Iterable<HotGroup> findAllByStatus(int status) {
		return hotGroupRepository.findAllByStatus(status);
	}
}
