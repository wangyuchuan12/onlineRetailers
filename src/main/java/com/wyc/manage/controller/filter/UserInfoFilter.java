package com.wyc.manage.controller.filter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.manage.controller.session.SessionManager;
import com.wyc.service.TokenService;
import com.wyc.smart.service.UserSmartService;
import com.wyc.wx.domain.Token;
import com.wyc.wx.domain.UserInfo;
import com.wyc.wx.domain.WxContext;

public class UserInfoFilter extends Filter{

	@Autowired
	private UserSmartService userSmartService;
	
	@Autowired
	private TokenService tokenService;
	

	@Autowired
	private WxContext wxContext;
	
	final static Logger logger = LoggerFactory.getLogger(UserInfoFilter.class);
	@Override
	public Object handlerBefore(SessionManager filterManager) throws Exception {
			
		
			try{
				
				UserInfo userInfo = null;
				
				HttpServletRequest httpServletRequest = filterManager.getHttpServletRequest();
				String tokenId = httpServletRequest.getParameter("token");
				
				logger.debug("tokenId:{}",tokenId);
		        Token token = tokenService.findByIdAndInvalidDateGreaterThan(tokenId, new DateTime());
		        if(token!=null){
		            userInfo = userSmartService.getFromDatabase(tokenId);
		            logger.debug("get userInfo from database by token {} , return object is {}",tokenId , userInfo);
		        }
		        String code = httpServletRequest.getParameter("code");
		        userSmartService.setCode(code);
		        String key = userSmartService.generateKey();
		        if(userInfo==null&&key!=null){
		            userInfo = userSmartService.getFromDatabaseByKey(key);
		            logger.debug("get userInfo from database by key {} , return object is {}",key , userInfo);
		        }
	            if(userInfo==null&&code!=null){
	                try {
	                    userInfo = userSmartService.getFromWx();
	                    logger.debug("before handle nickname is {}",userInfo.getNickname());
	                    userInfo.setNickname(StringEscapeUtils.escapeSql(userInfo.getNickname()));
	                    logger.debug("after handle nickname is {}",userInfo.getNickname());
	                    token = userSmartService.saveToDatabase(userInfo, key);
	                    logger.debug("save to database success ,the key is {} , the token is {}" , key , token);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    logger.error("get userInfo from wx has error");
	                    userInfo.setNickname("无法识别");
	                    //做最后一层保障，保证在数据库当中有userInfo
	                    token = userSmartService.saveToDatabase(userInfo, key);
	                }
	                
	            }  
	            logger.debug("after userInfo:"+userInfo);
	            return userInfo;
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
			
	}

	@Override
	public Object handlerAfter(SessionManager filterManager) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handlerPrivateException(SessionManager filterManager, Method method, Exception e) {
		e.printStackTrace();
		return null;
	}

	@Override
	public Object handlerPublicException(SessionManager filterManager, Exception e) {
		e.printStackTrace();
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> filterClasses = new ArrayList<>();
		filterClasses.add(WxConfigFilter.class);
		return filterClasses;
	}

}
