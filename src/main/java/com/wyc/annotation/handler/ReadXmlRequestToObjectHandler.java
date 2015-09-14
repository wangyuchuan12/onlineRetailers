package com.wyc.annotation.handler;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.wyc.annotation.ResultBean;
import com.wyc.intercept.domain.MyHttpServletRequest;

public class ReadXmlRequestToObjectHandler implements Handler{
    @Override
    public Object handle(HttpServletRequest httpServletRequest)
            throws Exception {
        SAXBuilder saxBuilder = new SAXBuilder();
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        Method method = myHttpServletRequest.getInvokeMethod();
        Document document = saxBuilder.build(httpServletRequest.getInputStream());
        Element rootElement = document.getRootElement();
        ResultBean resultBean = method.getAnnotation(ResultBean.class);
        Class<?> bean = resultBean.bean();
        Object target = bean.newInstance();
        for(Field field:bean.getDeclaredFields()){
           Column column = field.getAnnotation(Column.class);
           if(column!=null){
               String name = column.name();
               if(name.equals("")){
                   name = field.getName();
               }
               String value = rootElement.getChildText(name);
               field.setAccessible(true);
               field.set(target, value);
           }
        }
        
        myHttpServletRequest.setRequestObject(bean, target);
        return target;
    }

    @Override
    public Class<? extends Handler>[] extendHandlers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAnnotation(Annotation annotation) {
       
    }
    
}
