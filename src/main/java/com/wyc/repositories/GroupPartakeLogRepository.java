package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.GroupPartakeLog;

public interface GroupPartakeLogRepository extends CrudRepository<GroupPartakeLog, String>{


	Iterable<GroupPartakeLog> findAllByGroupIdOrderByHappenTimeAsc(String id);

}
