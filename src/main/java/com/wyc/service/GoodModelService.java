package com.wyc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.repositories.GoodModelRepository;

@Service
public class GoodModelService {
	@Autowired
	private GoodModelRepository goodModelRepository;
} 
