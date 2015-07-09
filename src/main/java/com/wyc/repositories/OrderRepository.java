package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;
import com.wyc.domain.Order;

public interface OrderRepository extends CrudRepository<Order, String>{

}
