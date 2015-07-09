package com.wyc.test;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.ApplicationContextProvider;
import com.wyc.util.TypeUtil;

@RestController
public class PowerAction {
    @Autowired
    private ApplicationContextProvider applicationContextProvider;

    @RequestMapping("/test/test_method")
    public Object testMethod(HttpServletRequest request) {
        
        String className = request.getParameter("class_name");
        String args = request.getParameter("args");
        String method = request.getParameter("method");
        ApplicationContext applicationContext = applicationContextProvider
                .getApplicationContext();
        try {
            Class<?> clazz = Class.forName(className);
            Object service = applicationContext.getBean(clazz);
            if(service==null){
                service = clazz.newInstance();
            }
            
            String[] params = null;
            if(args!=null&&!args.trim().equals("")){
                params = args.split(",");
            }
            if (params == null || params.length == 0) {
                Method noParmMethod = clazz.getMethod(method);
                if (noParmMethod != null) {
                    return noParmMethod.invoke(service);
                }
                return null;
            }
            for (Method methodRef : clazz.getMethods()) {
                Class<?>[] types = methodRef.getParameterTypes();
                if (methodRef.getName().equals(method)
                        && params.length == types.length) {     
                    if (params.length == 1) {

                        return methodRef.invoke(service,
                                TypeUtil.getValue(params[0], types[0]));
                    } else if (params.length == 2) {
                        return methodRef.invoke(service,
                                TypeUtil.getValue(params[0], types[0]),
                                TypeUtil.getValue(params[1], types[1]));
                    } else if (params.length == 3) {
                        return methodRef.invoke(service,
                                TypeUtil.getValue(params[0], types[0]),
                                TypeUtil.getValue(params[1], types[1]),
                                TypeUtil.getValue(params[2], types[2]));
                    }else{
                        System.out.println("可能参数太多了");
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("没有匹配的方法");
        return null;
    }
}
