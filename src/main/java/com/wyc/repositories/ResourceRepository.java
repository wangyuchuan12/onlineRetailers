package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;
import com.wyc.domain.Resource;

public interface ResourceRepository extends CrudRepository<Resource, String>{

}
