package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;
import com.wyc.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String>{

    public Customer findByOpenId(String openId);

}
