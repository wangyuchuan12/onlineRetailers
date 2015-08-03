package com.wyc.intercept.service;

import com.wyc.wx.domain.Token;

public interface InterceptService<T>{
   public  T getFromWx()throws Exception;
   public  boolean localValid(String token)throws Exception;
   public  T getFromDatabase(String token)throws Exception;
   public  Token saveToDatabase(T t)throws Exception;
}
