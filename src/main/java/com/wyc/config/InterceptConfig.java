package com.wyc.config;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.session.Session;
import org.apache.taglibs.standard.lang.jstl.EnumeratedMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.danga.MemCached.MemCachedClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyc.annotation.AfterHandlerAnnotation;
import com.wyc.annotation.BeforeHandlerAnnotation;
import com.wyc.annotation.JsApiTicketAnnotation;
import com.wyc.annotation.NowPageRecordAnnotation;
import com.wyc.annotation.ResponseJson;
import com.wyc.annotation.ReturnUrl;
import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.annotation.WxChooseWxPay;
import com.wyc.annotation.WxConfigAnnotation;
import com.wyc.annotation.handler.Handler;
import com.wyc.defineBean.StopToAfter;
import com.wyc.domain.Customer;
import com.wyc.domain.ExceptionRecord;
import com.wyc.domain.GoodType;
import com.wyc.domain.TemporaryData;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerService;
import com.wyc.service.ExceptionRecordService;
import com.wyc.service.GoodTypeService;
import com.wyc.service.TemporaryDataService;
import com.wyc.service.TokenService;
import com.wyc.service.WxUserInfoService;
import com.wyc.smart.service.AccessTokenSmartService;
import com.wyc.smart.service.AuthorizeSmartService;
import com.wyc.smart.service.UserSmartService;
import com.wyc.smart.service.WxJsApiTicketSmartService;
import com.wyc.util.RequestFactory;
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
    @Autowired
    private RequestFactory requestFactory;
    @Autowired
    private TemporaryDataService temporaryDataService;
    @Autowired
    private ExceptionRecordService exceptionRecordService;
    @Autowired
    private GoodTypeService goodTypeService;
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
        String openid = null;
        Customer customer = null;
        if(userInfo!=null){
            openid = userInfo.getOpenid();
            customer = customerService.findByOpenId(openid);
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
        try {
            return aroundHandler(proceedingJoinPoint);
        } catch (Exception e) {
            e.printStackTrace();
            Object[] args  = proceedingJoinPoint.getArgs();
            MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)proceedingJoinPoint.getArgs()[0];
            UserInfo userInfo = myHttpServletRequest.getUserInfo();
            String tokenId = myHttpServletRequest.getParameter("token");
            ExceptionRecord exceptionRecord = new ExceptionRecord();
            exceptionRecord.setFromUrl(myHttpServletRequest.getRequestURI());
            exceptionRecord.setToken(tokenId);
            if(e!=null){
                if(e.getCause()!=null){
                    exceptionRecord.setMessage(e.getCause().getMessage());
                }
            }
            exceptionRecord.setOpenId(userInfo.getOpenid());
            Map<String, String[]> parameterMap = myHttpServletRequest.getParameterMap();
            StringBuffer sb = new StringBuffer();
            for(Entry<String, String[]> entry:parameterMap.entrySet()){
                sb.append("&"+entry.getKey()+"="+entry.getValue()[0]);
            }
            if(parameterMap.size()>0){
                sb.deleteCharAt(0);
            }
            exceptionRecord.setParams(sb.toString());
            exceptionRecord.setToken(tokenId);
            exceptionRecord.setUserName(userInfo.getNickname());
            
            exceptionRecordService.add(exceptionRecord);
            return "redirect:/main/good_list";
        }
        
    }
    
    public Object aroundHandler(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        Object target = proceedingJoinPoint.getTarget();
        logger.debug("around target is {}",target);
        Object[] args  = proceedingJoinPoint.getArgs();
        String str = null;
        HttpServletRequest httpServletRequest = (HttpServletRequest)args[0];
        logger.debug("the session id is {}",httpServletRequest.getSession().getId());
        logger.debug("the good_type param is {}",httpServletRequest.getParameter("good_type"));
        for(Object arg:args){
            str+=arg+",";
            
        }
        logger.debug("the args is {}",str);
        
        
        
        String prepareRedirect = httpServletRequest.getParameter("prepare_redirect");
        if(prepareRedirect!=null){
            StringBuffer sb = new StringBuffer();
            sb.append(prepareRedirect);
            if(sb.toString().contains("?")){
                Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
                for(Entry<String, String[]> entry:parameterMap.entrySet()){
                    sb.append("&"+entry.getKey()+"="+entry.getValue()[0]);
                    httpServletRequest.getSession().setAttribute(entry.getKey(),entry.getValue()[0]);
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
        myHttpServletRequest.setInvokeMethod(method);
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
            String url = null;
            if(httpServletRequest.getQueryString()!=null&&!httpServletRequest.getQueryString().toLowerCase().equals("null")){
                url = httpServletRequest.getRequestURL().toString()+"?"+httpServletRequest.getQueryString();
            }else{
                url = httpServletRequest.getRequestURL().toString();
            }
            logger.debug("config url is {}",url);
            String noncestr = "Wm3WZYTPz0wzccnW"+Math.random();
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
            httpServletRequest.setAttribute("noncestr", noncestr);
            httpServletRequest.setAttribute("appId", wxContext.getAppid());
            httpServletRequest.setAttribute("datetime", datetime);
        
        }
        
        
        UserInfo userInfo = null;
        if(method.getAnnotation(UserInfoFromWebAnnotation.class)!=null){
            
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
                    
                    throw e;
                }
                
            }
            
            if(userInfo==null){
                String requestUrl = myHttpServletRequest.getRequestURL().toString();
                StringBuffer urlBuffer = new StringBuffer();
                urlBuffer.append(requestUrl);
                if(paramMap.entrySet().size()>0){
                    urlBuffer.append("?");
                }
                for(Entry<String, String[]> entry:paramMap.entrySet()){
                    if(entry.getValue()!=null&&entry.getValue().length>0){
                        urlBuffer.append("&"+entry.getKey()+"="+entry.getValue()[0]);
                    }
                }
                if(urlBuffer.toString().contains("&")){
                    urlBuffer.deleteCharAt(urlBuffer.indexOf("&"));
                }
                
                String wxRequestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid="+wxContext.getAppid()+"&redirect_uri="+urlBuffer.toString()+"&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect";
                logger.debug("redirect to url [{}]",wxRequestUrl);
                return "redirect:"+wxRequestUrl;
            }
            
            Customer customer = customerService.findByOpenId(userInfo.getOpenid());
            HttpSession session = httpServletRequest.getSession();
            Enumeration<String> sessionAttributes = session.getAttributeNames();
            while(sessionAttributes.hasMoreElements()){
                String sessionName = sessionAttributes.nextElement();
                logger.debug("the session name is {} and the session value is {}",sessionName,session.getAttribute(sessionName));
            }
            Object goodTypeId = httpServletRequest.getSession().getAttribute("good_type");
            if(goodTypeId==null||goodTypeId.toString().trim().equals("")){
                
                goodTypeId = customer.getDefaultGoodType();
                logger.debug("get good_type attribute from http param and the value is {}",goodTypeId);
            }
            
            if(goodTypeId==null||goodTypeId.toString().trim().equals("")){
                List<GoodType> goodTypes = goodTypeService.findAllByIsDefault(true);
                if(goodTypes.size()>0){
                    GoodType goodType = goodTypes.get(0);
                    goodTypeId = goodType.getId();
                    logger.debug("get good_type from goodType about default");
                }else{
                    Iterable<GoodType> goodTypeIterable = goodTypeService.findAll();
                    Iterator<GoodType> goodIterator = goodTypeIterable.iterator();
                    GoodType goodType = goodIterator.next();
                    goodTypeId = goodType.getId();
                    logger.debug("get good_type from goodType of all");
                }
            }
            logger.debug("the good_type is {}",goodTypeId);
            customer.setDefaultGoodType(goodTypeId.toString());
            customerService.save(customer);
            
            
            httpServletRequest.setAttribute("goodType", customer.getDefaultGoodType());
            
            
            
            myHttpServletRequest.setUserInfo(userInfo);
            myHttpServletRequest.setToken(token);
        }
        
        if(method.getAnnotation(NowPageRecordAnnotation.class)!=null){
            NowPageRecordAnnotation nowPageRecordAnnotation = method.getAnnotation(NowPageRecordAnnotation.class);
            int page = nowPageRecordAnnotation.page();
            TemporaryData temporaryData = temporaryDataService.findByMyKeyAndName(userInfo.getOpenid(), "nowpage");
            
            
            if(temporaryData==null){
                temporaryData = new TemporaryData();
                temporaryData.setMykey(userInfo.getOpenid());
                temporaryData.setName("nowpage");
                temporaryData.setValue(page+"");
                temporaryDataService.add(temporaryData);
                
            }else{  
                TemporaryData fromPageTemporaryData = temporaryDataService.findByMyKeyAndName(userInfo.getOpenid(), "frompage");
                if(fromPageTemporaryData==null){
                    fromPageTemporaryData = new TemporaryData();
                    fromPageTemporaryData.setName("frompage");
                    fromPageTemporaryData.setMykey(userInfo.getOpenid());
                    fromPageTemporaryData.setValue(temporaryData.getValue());
                    temporaryDataService.add(fromPageTemporaryData);
                }else{
                    fromPageTemporaryData.setName("frompage");
                    fromPageTemporaryData.setMykey(userInfo.getOpenid());
                    fromPageTemporaryData.setValue(temporaryData.getValue());
                    temporaryDataService.save(fromPageTemporaryData);
                }
                temporaryData.setValue(page+"");
                temporaryDataService.save(temporaryData);
                
            }
            
            
        }
        
        if(method.getAnnotation(WxChooseWxPay.class)!=null){
           
      
            
        }
        if(method.getAnnotation(BeforeHandlerAnnotation.class)!=null){
            BeforeHandlerAnnotation beforeHandlerAnnotation = method.getAnnotation(BeforeHandlerAnnotation.class);
            Class<?>[] classes = beforeHandlerAnnotation.hanlerClasses();
            for(Class<?> clazz:classes){
                Method handleMethod = clazz.getMethod("handle", HttpServletRequest.class);
                Handler handleTarget = (Handler) clazz.newInstance();
                handleTarget.setAnnotation(beforeHandlerAnnotation);
                factory.autowireBean(handleTarget);
                Class<?>[] extendHandlers = handleTarget.extendHandlers();
                if(extendHandlers!=null){
                    for(Class<?> handlerClass:extendHandlers){
                        Handler extendHandlerTarget =(Handler)handlerClass.newInstance();
                        Method extendHandleMethod = handlerClass.getMethod("handle", HttpServletRequest.class);
                        factory.autowireBean(extendHandlerTarget);
                        extendHandleMethod.invoke(extendHandlerTarget, myHttpServletRequest);
                    }
                }
                handleMethod.invoke(handleTarget, myHttpServletRequest);
            }
        }
        if(myHttpServletRequest!=null){
            args[0] = myHttpServletRequest;
        }
        if(token!=null){
            myHttpServletRequest.setAttribute("token", token);
        }
        Object returnValue = null;
        
        try {
            Object url = proceedingJoinPoint.proceed(args);
            logger.debug("return url is {}",url);
            
            if(url!=null&&url.getClass().equals(StopToAfter.class)){
                if(method.getAnnotation(ResponseJson.class)!=null){
                    ResponseJson responseJson = method.getAnnotation(ResponseJson.class);
                    Map<String, String> responseMap = new HashMap<String, String>();
                    for(String name:responseJson.names()){
                        responseMap.put(name, httpServletRequest.getAttribute(name).toString());
                    }
                    HttpServletResponse httpServletResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(responseMap);
                    httpServletResponse.getWriter().write(json);
                    return null;
                }
                
                
                
                
                if(method.getAnnotation(ReturnUrl.class)!=null){
                    ReturnUrl returnUrl = method.getAnnotation(ReturnUrl.class);
                    return returnUrl.url();
                }
                return null;
            }
            returnValue = url;
        } catch (Exception e) {
            logger.error("invoke action method has error");
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            StringBuffer errorBuffer = new StringBuffer();
            for(StackTraceElement stackTraceElement:stackTraceElements){
                errorBuffer.append("in line"+stackTraceElement.getLineNumber()+",the method is:"+stackTraceElement.getMethodName()+"   errinfo:"+stackTraceElement.toString());
                errorBuffer.append("\r\n");
            }
            
            logger.error(errorBuffer.toString());
            throw e;
        }
        if(method.getAnnotation(AfterHandlerAnnotation.class)!=null){
            AfterHandlerAnnotation afterHandlerAnnotation = method.getAnnotation(AfterHandlerAnnotation.class);
            Class<?>[] classes = afterHandlerAnnotation.hanlerClasses();
            for(Class<?> clazz:classes){
                Method handleMethod = clazz.getMethod("handle", HttpServletRequest.class);
                Handler handleTarget = (Handler) clazz.newInstance();
                handleTarget.setAnnotation(afterHandlerAnnotation);
                factory.autowireBean(handleTarget);
                Class<?>[] extendHandlers = handleTarget.extendHandlers();
                if(extendHandlers!=null){
                    for(Class<?> handlerClass:extendHandlers){
                        Handler extendHandlerTarget =(Handler)handlerClass.newInstance();
                        Method extendHandleMethod = handlerClass.getMethod("handle", HttpServletRequest.class);
                        factory.autowireBean(extendHandlerTarget);
                        extendHandleMethod.invoke(extendHandlerTarget, myHttpServletRequest);
                    }
                }
                returnValue = handleMethod.invoke(handleTarget, myHttpServletRequest);
            }
        }
        ResponseJson responseJson = method.getAnnotation(ResponseJson.class);
        logger.debug("responseJson is {}",responseJson);
        if(responseJson!=null){
            logger.debug("has ResponseJson annotation");
            Map<String, String> responseMap = new HashMap<String, String>();
            for(String name:responseJson.names()){
                responseMap.put(name, httpServletRequest.getAttribute(name).toString());
            }
            HttpServletResponse httpServletResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(responseMap);
            httpServletResponse.getWriter().write(json);
            logger.debug("response json:{}",json);
            return null;
        }
        
        if(method.getAnnotation(ReturnUrl.class)!=null){
            ReturnUrl returnUrl = method.getAnnotation(ReturnUrl.class);
            return returnUrl.url();
        }
        logger.debug("return value:{}",returnValue);
        return returnValue;
    }
}
