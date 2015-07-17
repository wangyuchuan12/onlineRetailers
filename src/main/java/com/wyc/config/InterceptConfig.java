package com.wyc.config;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class InterceptConfig {
    @Before(value="execution (* com.wyc.controller.action.*.*(..))")
    public void beforeAction(JoinPoint joinPoint)throws Exception{
       System.out.println(joinPoint.getKind());
       System.out.println(joinPoint.getTarget());
    }
    
    @Before(value="execution (* com.wyc.controller.api.*.*(..))")
    public void beforeApi(JoinPoint joinPoint)throws Exception{
        System.out.println(joinPoint.getKind());
        System.out.println(joinPoint.getTarget());
     }
}
