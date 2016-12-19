package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.HotGroup;

public interface HotGroupRepository extends CrudRepository<HotGroup, String>{

	Iterable<HotGroup> findAllByStatus(int status);

	HotGroup findOneByGroupId(String groupId);

}
