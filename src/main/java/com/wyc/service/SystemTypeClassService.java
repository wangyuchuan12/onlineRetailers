package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.SystemTypeClass;
import com.wyc.repositories.SystemTypeClassRepository;

@Service
public class SystemTypeClassService {
	@Autowired
	private SystemTypeClassRepository systemTypeClassRepository;

	public SystemTypeClass findOne(String id ) {
		return systemTypeClassRepository.findOne(id);
	}
}
