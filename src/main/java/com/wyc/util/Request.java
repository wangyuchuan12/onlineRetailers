package com.wyc.util;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Request {
	private String charsetName = "UTF-8";
	@Autowired
	private HttpClient httpClient;
	private URL url;
	final static Logger logger = LoggerFactory.getLogger(Request.class);
	public Request(URL url){
		this.url = url;
		
	}
	
	public Response get(Map<String, String> params) throws Exception{
	        HttpGet httpGet = new HttpGet(url.getPath());
	        logger.debug(url.getHost());
	        logger.debug(url.getPath());
	        logger.debug(url.getProtocol());
	        logger.debug(url.getQuery());
	        logger.debug(url.getRef());
	        logger.debug(url.getUserInfo());
	        logger.debug(url.getDefaultPort()+"");
	        logger.debug(url.getPort()+"");
	        logger.debug(url.toURI().getPath());
	        logger.debug(url.toURI().getRawPath());
	        logger.debug(url.toURI().getRawAuthority());
	        logger.debug(url.toURI().getAuthority());
	        logger.debug(url.toURI().getFragment());
	        logger.debug(url.toURI().getRawQuery());
	        logger.debug(url.toString());
	        logger.debug(url.toURI().toString());
	        HttpParams hp = httpGet.getParams();    
	        hp.getParameter("true");
	        if(params!=null){
        	        for(Entry<String, String> param:params.entrySet()){
        	            hp.setParameter(param.getKey(), param.getValue());
        	        }
	        }
	        HttpResponse httpResponse = httpClient.execute(httpGet);
	        HttpEntity httpEntity = httpResponse.getEntity();
		logger.debug("request success");
		return new Response(httpEntity.getContent(),charsetName);
	}
	
	public Response post(String data) throws IOException{
	        HttpPost httpPost = new HttpPost(url.getPath());
	        StringEntity entity = new StringEntity(data,charsetName);
	        entity.setContentEncoding(charsetName);
	        httpPost.setEntity(entity);
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        
	        HttpEntity httpEntity = httpResponse.getEntity();
		return new Response(httpEntity.getContent(),charsetName);
		
	}
}
