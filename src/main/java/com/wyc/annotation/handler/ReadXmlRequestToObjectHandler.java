package com.wyc.annotation.handler;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.wyc.annotation.ReadXmlRequestToObjectAnnotation;
import com.wyc.intercept.domain.MyHttpServletRequest;

public class ReadXmlRequestToObjectHandler implements Handler{
    private Annotation annotation;
    @Override
    public Object handle(HttpServletRequest httpServletRequest)
            throws Exception {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(httpServletRequest.getInputStream());
        Element rootElement = document.getRootElement();
        ReadXmlRequestToObjectAnnotation readXmlRequestToObjectAnnotation = (ReadXmlRequestToObjectAnnotation)annotation;
        Class<?> bean = readXmlRequestToObjectAnnotation.bean();
        Object target = bean.newInstance();
        for(Field field:bean.getFields()){
           Column column = field.getAnnotation(Column.class);
           String name = column.name();
           String value = rootElement.getAttributeValue(name);
           field.set(target, value);
        }
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
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
        this.annotation = annotation;
    }
    
}
