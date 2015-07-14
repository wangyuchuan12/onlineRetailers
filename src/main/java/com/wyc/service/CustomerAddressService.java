package com.wyc.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wyc.domain.CustomerAddress;
import com.wyc.repositories.CustomerAddressRepository;

@Service
public class CustomerAddressService {
    @Autowired
    private CustomerAddressRepository customerAddressRepository;
    public void add(CustomerAddress customerAddress){
        customerAddress.setCreateAt(new DateTime());
        customerAddressRepository.save(customerAddress);
    }
    
    public void update(CustomerAddress customerAddress){
        customerAddress.setUpdateAt(new DateTime());
        customerAddressRepository.save(customerAddress);
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
}
