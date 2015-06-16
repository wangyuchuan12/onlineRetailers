package com.wyc.controller.api;

import java.io.BufferedInputStream;
import java.io.InputStream;
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
	public String test(HttpServletRequest servletRequest)throws Exception {
		InputStream inputStream = servletRequest.getInputStream();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		byte[] b= new byte[1024];
		int i = 0;
		while((i=bufferedInputStream.read(b))>0){
			String string = new String(b,0, i);
			System.out.println(string);
		}
		return null;
		

	}
}
