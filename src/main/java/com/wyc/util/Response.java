package com.wyc.util;

import java.io.InputStream;

public class Response {
	private InputStream inputStream;
	private String charsetName = "UTF-8";
	public Response(InputStream inputStream){
		this.inputStream = inputStream;
	}
	public Response(InputStream inputStream , String charsetName){
		this.inputStream = inputStream;
		this.charsetName = charsetName;
	}
	public String read()throws Exception{
		byte[] jsonBytes = new byte[this.inputStream.available()];
		inputStream.read(jsonBytes);
		String message = new String(jsonBytes,charsetName);
		return message;
	}
}
