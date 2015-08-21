package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.CustomerAddress;
import com.wyc.repositories.CustomerAddressRepository;

@Service
public class CustomerAddressService {
    @Autowired
    private CustomerAddressRepository customerAddressRepository;
    public CustomerAddress add(CustomerAddress customerAddress){
        customerAddress.setId(UUID.randomUUID().toString());
        customerAddress.setCreateAt(new DateTime());
        customerAddress.setUpdateAt(new DateTime());
        return customerAddressRepository.save(customerAddress);
    }
    
    public CustomerAddress save(CustomerAddress customerAddress){
        customerAddress.setUpdateAt(new DateTime());
        return customerAddressRepository.save(customerAddress);
    }
    
    public void delete(CustomerAddress customerAddress){
        customerAddressRepository.delete(customerAddress);
    }
    
    public void delete(String id){
        customerAddressRepository.delete(id);
    }
    
    public CustomerAddress findOne(String id){
        return customerAddressRepository.findOne(id);
    }
    
    public Iterable<CustomerAddress> findAll(){
        return customerAddressRepository.findAll();
    }
    
    public Iterable<CustomerAddress> findByCustomerId(String customerId){
        return customerAddressRepository.findAllByCustomerId(customerId);
    }
}
