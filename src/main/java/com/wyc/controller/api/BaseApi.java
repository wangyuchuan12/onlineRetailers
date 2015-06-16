package com.wyc.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseApi {
		@RequestMapping(value = "/api/test")
		public void test(@RequestParam(value="signature")String signature,
				@RequestParam(value="timestamp")String timestamp,
				@RequestParam(value="nonce")String nonce,
				@RequestParam(value="echostr")String echostr){
			System.out.println(signature);
			System.out.println(timestamp);
			System.out.println(nonce);
			System.out.println(echostr);
			
		}
}
