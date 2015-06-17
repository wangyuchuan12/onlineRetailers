package com.wyc.controller.api;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseApi {
	private String token = "onlineRetailers";

	@RequestMapping(value = "/api/test")
	public String test(HttpServletRequest servletRequest) {
		String signature = servletRequest.getParameter("signature");
		String timestamp = servletRequest.getParameter("timestamp");
		String nonce = servletRequest.getParameter("nonce");
		String echostr = servletRequest.getParameter("echostr");
		System.out.println("ceshi");
		System.out.println(signature);
		System.out.println(timestamp);
		System.out.println(nonce);
		System.out.println(echostr);
		return echostr;

	}
}
