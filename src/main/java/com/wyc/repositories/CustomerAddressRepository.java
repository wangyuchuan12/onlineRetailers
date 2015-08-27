package com.wyc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.domain.CustomerAddress;

public interface CustomerAddressRepository extends CrudRepository<CustomerAddress, String>{

    public Iterable<CustomerAddress> findAllByCustomerIdOrderByCreateAtDesc(String customerId);
    
    @Query("select count(*) from com.wyc.domain.CustomerAddress g where g.customerId = :customerId")
    public int countByCustomerId(@Param("customerId")String customerId);

}
