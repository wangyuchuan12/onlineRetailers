package com.wyc.repositories;

import org.springframework.data.repository.CrudRepository;

import com.wyc.domain.CustomerAddress;

public interface CustomerAddressRepository extends CrudRepository<CustomerAddress, String>{

    public Iterable<CustomerAddress> findAllByCustomerId(String customerId);

}
