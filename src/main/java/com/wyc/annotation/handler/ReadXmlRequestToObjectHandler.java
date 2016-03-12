package com.wyc.annotation.handler;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wyc.annotation.ResultBean;
import com.wyc.controller.action.GoodsAction;
import com.wyc.intercept.domain.MyHttpServletRequest;

public class ReadXmlRequestToObjectHandler implements Handler{
	final static Logger logger = LoggerFactory.getLogger(ReadXmlRequestToObjectHandler.class);
    @Override
    public Object handle(HttpServletRequest httpServletRequest)
            throws Exception {
    	try {
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
		} catch (Exception e) {
			logger.debug("count not readXml to object");
		}
    	return null;
        
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
