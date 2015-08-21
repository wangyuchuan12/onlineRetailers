package com.wyc.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wyc.domain.Customer;
import com.wyc.repositories.CustomerRepository;
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    final static Logger logger = LoggerFactory.getLogger(CustomerService.class);
    public Customer add(Customer customer){
        customer.setId(UUID.randomUUID().toString());
        customer.setCreateAt(new DateTime());
        customer.setUpdateAt(new DateTime());
        logger.debug("add customer the id is {},the openid is {}",customer.getId(),customer.getOpenId());
        return customerRepository.save(customer);
    }
    
    public Customer save(Customer customer){
        customer.setUpdateAt(new DateTime());
        return customerRepository.save(customer);
    }
    
    public void delete(Customer customer){
        customerRepository.delete(customer);
    }
    
    public void delete(String id){
        customerRepository.delete(id);
    }
    
    public Customer findOne(String id){
        return customerRepository.findOne(id);
    }
    
    public Iterable<Customer> findAll(){
        return customerRepository.findAll();
    }
    
    public Customer findByOpenId(String openId){
        return customerRepository.findByOpenId(openId);
    }
}
