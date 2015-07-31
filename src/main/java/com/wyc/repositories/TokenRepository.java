package com.wyc.repositories;

import org.joda.time.DateTime;
import org.springframework.data.repository.CrudRepository;

import com.wyc.wx.domain.Token;



public interface TokenRepository extends CrudRepository<Token, String>{
    public Token findByIdAndInvalidDateGreaterThan(String id , DateTime dateTime);
}
