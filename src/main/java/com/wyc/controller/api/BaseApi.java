package com.wyc.controller.api;
import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class BaseApi {
	@RequestMapping(value = "/api/test")
	public String test(HttpServletRequest servletRequest)throws Exception {
		String signature = servletRequest.getParameter("signature");
		String timestamp = servletRequest.getParameter("timestamp");
		String nonce = servletRequest.getParameter("nonce");
		String echostr = servletRequest.getParameter("echostr");
		System.out.println("...........................get........................................");
		System.out.println(signature);
		System.out.println(timestamp);
		System.out.println(nonce);
		System.out.println(echostr);
		System.out.println("............................post........................................");
		try {
		    SAXBuilder saxBuilder = new SAXBuilder();
	                Document document = saxBuilder.build(servletRequest.getInputStream());
	                System.out.println(document.toString());
	                
	                XMLOutputter xmlOutputter = new XMLOutputter();
	                xmlOutputter.output(document, System.out);
                } catch (Exception e) {
                    System.out.println("。。。。。。出了点小问题。。。。。。。");
                }
		
		return echostr;

	}
}
