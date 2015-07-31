package com.wyc.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.repositories.TokenRepository;
import com.wyc.wx.domain.Token;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;
    
    public Token findByIdAndInvalidDateGreaterThan(String id , DateTime dateTime){
        return tokenRepository.findByIdAndInvalidDateGreaterThan(id , dateTime);
    }
    
    public Token add(Token token){
        token.setCreateAt(new DateTime());
        token.setUpdateAt(new DateTime());
        return tokenRepository.save(token);
    }
    
    public Token save(Token token){
        token.setUpdateAt(new DateTime());
        return tokenRepository.save(token);
    }
}
