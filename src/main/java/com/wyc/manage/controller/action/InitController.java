package com.wyc.manage.controller.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/manager/")
public class InitController {

	@RequestMapping(value="/index")
	public String index(HttpServletRequest httpServletRequest){
		return "manager/Index";
	}
}
