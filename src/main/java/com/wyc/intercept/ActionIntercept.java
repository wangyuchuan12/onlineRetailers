package com.wyc.intercept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wyc.intercept.domain.AfterBean;
import com.wyc.intercept.domain.BeforeBean;
import com.wyc.intercept.domain.ResponseBean;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.domain.UserInfo;
import com.wyc.wx.service.OauthService;
import com.wyc.wx.service.UserService;
public  class ActionIntercept extends BaseActionIntercept{
    private OauthService oauthService;
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(ActionIntercept.class);
    public void setOauthService(OauthService oauthService) {
        this.oauthService = oauthService;
    }
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    public BeforeBean before() {
        BeforeBean bean = new BeforeBean();
        try {
            String code = getParameter("code");
            Authorize authorize = oauthService.getAuthorizeByCode(code);
            if(authorize!=null){
                bean.setEnalble(true);
                UserInfo userInfo = userService.getUserInfoFromWeb(authorize.getAccess_token(), authorize.getOpenid(), 1);
                bean.setUserInfo(userInfo);
            }else{
                bean.setEnalble(false);
            }
           
        } catch (Exception e) {
            logger.error("execute error", e);
            exception(e);
            bean.setEnalble(false);
        }
        return bean;
    }
    
    @Override
    public ResponseBean response(BeforeBean beforeBean) {
        httpServletRequest.setAttribute("user_info", beforeBean.getUserInfo());
        ResponseBean responseBean = new ResponseBean();
        responseBean.getResponseMap().put("user_info", beforeBean.getUserInfo());
        return responseBean;
    }
    
    @Override
    public AfterBean after(ResponseBean responseBean) {
        return new AfterBean();
    }
    @Override
    public String exception(Exception e) {
        // TODO Auto-generated method stub
        return null;
    }
}
