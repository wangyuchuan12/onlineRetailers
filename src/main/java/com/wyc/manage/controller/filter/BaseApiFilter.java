package com.wyc.manage.controller.filter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyc.manage.controller.session.SessionManager;
import com.wyc.manage.domain.vo.ResultVo;
import com.wyc.wx.domain.UserInfo;

public class BaseApiFilter extends Filter{

	@Override
	public Object handlerBefore(SessionManager filterManager) throws Exception {
		UserInfo userInfo = (UserInfo)filterManager.getObject(UserInfo.class);
		
		if(userInfo==null){
			filterManager.setEnd(true);
			
			ResultVo resultVo = new ResultVo();
			resultVo.setSuccess(false);
			resultVo.setMsg("找不到用户信息，请用微信客户端登录");
			filterManager.setReturnValue(resultVo);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handlerPublicException(SessionManager filterManager, Exception e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Class<? extends Filter>> dependClasses() {
		List<Class<? extends Filter>> filters = new ArrayList<>();
		filters.add(UserInfoFilter.class);
		return filters;
	}

}
