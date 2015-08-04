package com.wyc.smart.service;

import com.wyc.wx.domain.Token;
import com.wyc.wx.domain.UserInfo;

public interface SmartService<T>{
   public  T getFromWx()throws Exception;
   public  boolean localValid(String token)throws Exception;
   public  T getFromDatabase(String token)throws Exception;
   public  Token saveToDatabase(T t)throws Exception;
   public UserInfo getFromDatabaseByOther();
   
   public UserInfo getFromDatabaseByKey(String key);
   public String generateKey(String ... args);
   
   public boolean duplicate(String key);
}
