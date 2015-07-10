package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;
import com.wyc.domain.GoodOrder;

public interface OrderRepository extends CrudRepository<GoodOrder, String>{

}
