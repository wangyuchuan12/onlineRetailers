package com.wyc.controller.api;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseApi {
	private String token = "onlineRetailers";

	@RequestMapping(value = "/api/test")
	public String test(HttpServletRequest servletRequest) {
		Map<String,String[]> map = servletRequest.getParameterMap();
		for(Entry<String, String[]> entry:map.entrySet()){
			System.out.println(entry.getKey());
			System.out.println("....");
			for(String str:entry.getValue()){
				System.out.println(str);
			}
		}
		return null;

	}
}
