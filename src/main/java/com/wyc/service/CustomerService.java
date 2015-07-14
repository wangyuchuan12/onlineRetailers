package com.wyc.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.domain.Customer;
import com.wyc.repositories.CustomerRepository;
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    public void add(Customer customer){
        customer.setCreateAt(new DateTime());
        customerRepository.save(customer);
    }
    
    public void update(Customer customer){
        customer.setUpdateAt(new DateTime());
        customerRepository.save(customer);
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
}
