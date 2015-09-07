package com.wyc.config;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.danga.MemCached.MemCachedClient;
import com.wyc.annotation.AccessTokenAnnotation;
import com.wyc.annotation.AuthorizeAnnotation;
import com.wyc.annotation.JsApiTicketAnnotation;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.annotation.WxConfigAnnotation;
import com.wyc.domain.Customer;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerService;
import com.wyc.service.TokenService;
import com.wyc.service.WxUserInfoService;
import com.wyc.smart.service.AccessTokenSmartService;
import com.wyc.smart.service.AuthorizeSmartService;
import com.wyc.smart.service.UserSmartService;
import com.wyc.smart.service.WxJsApiTicketSmartService;
import com.wyc.wx.domain.AccessTokenBean;
import com.wyc.wx.domain.Authorize;
import com.wyc.wx.domain.JsapiTicketBean;
import com.wyc.wx.domain.Token;
import com.wyc.wx.domain.UserInfo;
import com.wyc.wx.domain.WxContext;
import com.wyc.wx.service.OauthService;
import com.wyc.wx.service.UserService;

@Aspect
@Component
public class InterceptConfig {
    @Autowired
    private UserService userService;
    @Autowired
    private OauthService oauthService;
    @Autowired
    private WxContext wxContext;
    @Autowired
    private MemCachedClient memCachedClient;
    @Autowired
    private AutowireCapableBeanFactory factory;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AccessTokenSmartService accessTokenSmartService;
    @Autowired
    private AuthorizeSmartService authorizeSmartService;
    @Autowired
    private UserSmartService userSmartService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private WxJsApiTicketSmartService wxJsApiTicketSmartService;
    final static Logger logger = LoggerFactory.getLogger(InterceptConfig.class);
    
 //   @Around(value="execution (* com.wyc.wx.service.*.*(..))")
    public Object aroundWxService(ProceedingJoinPoint proceedingJoinPoint){
            logger.debug("execution (* com.wyc.wx.service.*.*(..))");
            Object[] args = proceedingJoinPoint.getArgs();
            StringBuffer sb = new StringBuffer();
            sb.append(proceedingJoinPoint.getTarget().getClass());
            sb.append("-");
            for(Object obj:args){
                if(obj!=null){
                    sb.append(obj);
                }
            }
            Object object = memCachedClient.get(sb.toString());
            if(object==null){
                try {
                    object = proceedingJoinPoint.proceed(args);
                    memCachedClient.set(sb.toString(), object);
                    logger.debug("get "+object+" from "+"database");
                } catch (Throwable e) {
                    StringBuffer sb2 = new StringBuffer();
                    for(StackTraceElement stackTraceElement:e.getStackTrace()){
                        sb2.append(stackTraceElement+"\r\n");
                    }
                    logger.error(sb2.toString());
                }
              
            }else{
                logger.debug("get "+object+" from "+"memCachedClient");
            }
       
        return object;
    }
    
    @Before(value="execution (* com.wyc.controller.action.*.*(..))")
    public void beforeAction(JoinPoint joinPoint){
        MyHttpServletRequest httpServletRequest = (MyHttpServletRequest)joinPoint.getArgs()[0];
        UserInfo userInfo = httpServletRequest.getUserInfo();
        if(userInfo!=null){
            String openid = userInfo.getOpenid();
            Customer customer = customerService.findByOpenId(openid);
            if(customer==null){
                customer = new Customer();
                customer.setOpenId(openid);
                customer = customerService.add(customer);
                logger.debug("add customer to database and openid = {},id = {}",openid,customer.getId());
            }
        }
    }
    
    @Around(value="execution (* com.wyc.controller.api.*.*(..))")
    public Object aroundApi(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
    	return aroundAction(proceedingJoinPoint);
    }
    
    @Around(value="execution (* com.wyc.controller.action.*.*(..))")
    public Object aroundAction(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        Object target = proceedingJoinPoint.getTarget();
        logger.debug("around target is {}",target);
        Object[] args  = proceedingJoinPoint.getArgs();
        String str = null;
        for(Object arg:args){
            str+=arg+",";
        }
        logger.debug("the args is {}",str);
        HttpServletRequest httpServletRequest = (HttpServletRequest)args[0];
        String prepareRedirect = httpServletRequest.getParameter("prepare_redirect");
        if(prepareRedirect!=null){
            StringBuffer sb = new StringBuffer();
            sb.append(prepareRedirect);
            if(sb.toString().contains("?")){
                Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
                for(Entry<String, String[]> entry:parameterMap.entrySet()){
                    sb.append("&"+entry.getKey()+"="+entry.getValue()[0]);
                }
            }
            logger.debug("the prepareRedirect is {}",sb.toString());
            httpServletRequest.setAttribute("prepareRedirect", sb.toString());
        }
        String remoteAddress = httpServletRequest.getRemoteAddr();
        logger.debug("remoteAddress is {}",remoteAddress);
        Method method = null;
        
        for(Method oneMethod:target.getClass().getMethods()){
            if(oneMethod.getName().equals(proceedingJoinPoint.getSignature().getName())){
                method = oneMethod;
                break;
            }
        }
        
        logger.debug("invoke the method is {}",method);
        MyHttpServletRequest myHttpServletRequest = new MyHttpServletRequest(httpServletRequest);
        String tokenId = myHttpServletRequest.getParameter("token");
        Token token = tokenService.findByIdAndInvalidDateGreaterThan(tokenId, new DateTime());
        logger.debug("the request url is {}",myHttpServletRequest.getRequestURI());
        java.util.Map<String, String[]> paramMap = myHttpServletRequest.getParameterMap();
        StringBuffer sb = new StringBuffer();
        sb.append("the parameter is ");
        for(Entry<String, String[]> entry:paramMap.entrySet()){
            if(entry.getValue()!=null&&entry.getValue().length>0){
                sb.append("&");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue()[0]);
            }
        }
        logger.debug(sb.toString());
//        if(method.getAnnotation(AccessTokenAnnotation.class)!=null){
//            
//            AccessTokenBean accessTokenBean = null;
//            if(token!=null){
//                accessTokenBean = accessTokenSmartService.getFromDatabase(tokenId);
//                logger.debug("get accessTokenBean from database by token {} , return object is {}",tokenId , accessTokenBean);
//            }
//            String accessToken = myHttpServletRequest.getParameter("access_token");
//            accessTokenSmartService.setAccessToken(accessToken);
//            if(accessTokenBean==null&&accessToken!=null){
//                accessTokenBean = accessTokenSmartService.getFromDatabaseByOther();
//                logger.debug("get accessTokenBean from database by accessToken {} , return object is {}",accessToken , accessTokenBean);
//            }
//            String key = accessTokenSmartService.generateKey();
//            if(accessTokenBean==null&&key!=null){
//                
//                accessTokenBean = accessTokenSmartService.getFromDatabaseByKey(key);
//                logger.debug("get accessTokenBean from database by key {} , return object is {}",key , accessTokenBean);
//            }
//            
//            if(accessTokenBean==null){
//                try {
//                    accessTokenBean = accessTokenSmartService.getFromWx();
//                    logger.debug("get accessTokenBean from wx , return object is {}", accessTokenBean);
//                } catch (Exception e) {
//                    logger.error("get accessTokenBean from wx has error");
//                    e.printStackTrace();
//                }
//                
//                token = accessTokenSmartService.saveToDatabase(accessTokenBean, key);
//                logger.debug("save to database success ,the key is {} , the token is " , key , token);
//                
//            }
//            myHttpServletRequest.setAccessTokenBean(accessTokenBean);
//        }
//        if(method.getAnnotation(AuthorizeAnnotation.class)!=null){
//            Authorize authorize = null;
//            if(token!=null){
//                authorize = authorizeSmartService.getFromDatabase(tokenId);
//                logger.debug("get authorize from database by token {} , return object is {}",tokenId , authorize);
//            }
//            String key = authorizeSmartService.generateKey();
//            if(authorize==null&&key!=null){
//                authorize = authorizeSmartService.getFromDatabaseByKey(key);
//                logger.debug("get authorize from database by key {} , return object is {}",key , authorize);
//            }
//            if(authorize==null){
//                try {
//                    authorize = authorizeSmartService.getFromWx();
//                } catch (Exception e) {
//                    logger.error("get authorize from wx has error");
//                    e.printStackTrace();
//                }
//                token = authorizeSmartService.saveToDatabase(authorize, key);
//                logger.debug("save to database success ,the key is {} , the token is " , key , token);
//            }
//            myHttpServletRequest.setAuthorize(authorize);
//        }
        
        if(method.getAnnotation(JsApiTicketAnnotation.class)!=null){
            JsapiTicketBean jsapiTicketBean = wxJsApiTicketSmartService.getFromDatabase();
            if(jsapiTicketBean==null){
                jsapiTicketBean = wxJsApiTicketSmartService.getFromWx();
                jsapiTicketBean = wxJsApiTicketSmartService.addToDataBase(jsapiTicketBean);
            }
            if(!wxJsApiTicketSmartService.currentIsAvailable()){
                String id = jsapiTicketBean.getId();
                jsapiTicketBean = wxJsApiTicketSmartService.getFromWx();
                jsapiTicketBean.setId(id);
                jsapiTicketBean = wxJsApiTicketSmartService.saveToDataBase(jsapiTicketBean);
            }
            myHttpServletRequest.setJsapiTicketBean(jsapiTicketBean);
        }
        
        if(method.getAnnotation(WxConfigAnnotation.class)!=null){
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            String datetime = String.valueOf(System.currentTimeMillis() / 1000);
           
            StringBuffer decript = new StringBuffer();
            String url = httpServletRequest.getRequestURL().toString();
            String noncestr = "Wm3WZYTPz0wzccnW";
            decript.append("jsapi_ticket=");
            decript.append(myHttpServletRequest.getJsapiTicketBean().getTicket()+"&");
            decript.append("noncestr=");
            decript.append(noncestr+"&");
            decript.append("timestamp=");
            decript.append(datetime+"&");
            decript.append("url=");
            decript.append(url);
        
            logger.debug("decript:{}",decript);
            logger.debug("jsapi_ticket:{}",myHttpServletRequest.getJsapiTicketBean().getTicket());
            logger.debug("noncestr:{}",noncestr);
            logger.debug("datetime:{}",datetime);
            logger.debug("url:{}",url);
            digest.reset();
            digest.update(decript.toString().getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer digestBuffer = new StringBuffer();
            for(byte b :messageDigest){
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    digestBuffer.append(0);
                }
                digestBuffer.append(shaHex);
            }
            logger.debug("signature:{}",digestBuffer.toString());
            httpServletRequest.setAttribute("signature", digestBuffer.toString());
            httpServletRequest.setAttribute("noncestr", "Wm3WZYTPz0wzccnW");
            httpServletRequest.setAttribute("appId", wxContext.getAppid());
            httpServletRequest.setAttribute("datetime", datetime);
        
        }
        
        
        if(method.getAnnotation(UserInfoFromWebAnnotation.class)!=null){
            UserInfo userInfo = null;
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
                    logger.error("get userInfo from wx has error");
                    userInfo.setNickname("无法识别");
                    //做最后一层保障，保证在数据库当中有userInfo
                    token = userSmartService.saveToDatabase(userInfo, key);
                }
                
            }
            
            if(userInfo==null){
                String requestUrl = myHttpServletRequest.getRequestURL().toString();
                StringBuffer urlBuffer = new StringBuffer();
                urlBuffer.append(requestUrl);
                urlBuffer.append("?");
                for(Entry<String, String[]> entry:paramMap.entrySet()){
                    if(entry.getValue()!=null&&entry.getValue().length>0){
                        urlBuffer.append("&"+entry.getKey()+"="+entry.getValue()[0]);
                    }
                }
                if(urlBuffer.toString().contains("&")){
                    urlBuffer.deleteCharAt(urlBuffer.indexOf("&"));
                }
                
                String wxRequestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=wx7e3ed2dc655c0145&redirect_uri="+urlBuffer.toString()+"&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect";
                logger.debug("redirect to url [{}]",wxRequestUrl);
                return "redirect:"+wxRequestUrl;
            }
            myHttpServletRequest.setUserInfo(userInfo);
            myHttpServletRequest.setToken(token);
        }
        
        
        
        if(myHttpServletRequest!=null){
            args[0] = myHttpServletRequest;
        }
        if(token!=null){
            myHttpServletRequest.setAttribute("token", token);
        }
        try {
            Object url = proceedingJoinPoint.proceed(args);
            logger.debug("return url is {}",url);
            return url;
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            logger.error("invoke action method has error");
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            StringBuffer errorBuffer = new StringBuffer();
            for(StackTraceElement stackTraceElement:stackTraceElements){
                errorBuffer.append("in line"+stackTraceElement.getLineNumber()+",the method is:"+stackTraceElement.getMethodName()+"   errinfo:"+stackTraceElement.toString());
                errorBuffer.append("\r\n");
            }
            
            logger.error(errorBuffer.toString());
            return null;
        }
    }
}
