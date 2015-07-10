package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;
import com.wyc.domain.MyResource;

public interface ResourceRepository extends CrudRepository<MyResource, String>{

}
