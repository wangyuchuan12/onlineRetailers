package com.wyc.util;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Map.Entry;

public class Request {
	private HttpURLConnection urlConnection;
	private String charsetName = "UTF-8";
	
	public Request(HttpURLConnection urlConnection){
		this.urlConnection = urlConnection;
		urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
	}
	
	public Request(HttpURLConnection urlConnection , String charsetName){
		this.urlConnection = urlConnection;
		this.charsetName = charsetName;
		urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
	}
	
	public Response get(Map<String, String> params) throws IOException{
		urlConnection.setRequestMethod("GET");
		urlConnection.connect();
		if(params!=null&&params.size()>0){
			for(Entry<String, String> param:params.entrySet()){
				urlConnection.setRequestProperty(param.getKey(), param.getValue());
			}
		}
		OutputStream outputStream = urlConnection.getOutputStream();
		outputStream.flush();
		outputStream.close();
		return new Response(urlConnection.getInputStream(),charsetName);
	}
	
	public Response post(String data) throws IOException{
		urlConnection.setRequestMethod("POST");
		urlConnection.connect();
		OutputStream outputStream = urlConnection.getOutputStream();
		outputStream.write(data.getBytes(charsetName));
		outputStream.flush();
		outputStream.close();
		return new Response(urlConnection.getInputStream(),charsetName);
		
	}
}
