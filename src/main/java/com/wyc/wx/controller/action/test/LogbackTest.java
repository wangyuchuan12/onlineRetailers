package com.wyc.wx.controller.action.test;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LogbackTest {
	final static Logger logger = LoggerFactory.getLogger(LogbackTest.class);
	
	@ResponseBody
	@RequestMapping("/test/testloadback")
	public Object testLogback(HttpServletRequest httpServletRequest){
		logger.debug("debug");
		logger.error("error");
		logger.info("info");
		System.out.println("system.out.println");
		return "success";
	}
}
