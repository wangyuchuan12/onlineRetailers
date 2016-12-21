package com.wyc.service;
import java.util.Set;

import org.joda.time.DateTime;
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

	public HotGroup findOneByGroupId(String groupId) {
		return hotGroupRepository.findOneByGroupId(groupId);
	}

	public void update(HotGroup hotGroup) {
		hotGroup.setUpdateAt(new DateTime());
		
		hotGroupRepository.save(hotGroup);
		
	}

	public Iterable<HotGroup> findAll(Set<String> ids) {
		return hotGroupRepository.findAll(ids);
	}
}
