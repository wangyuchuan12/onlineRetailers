package com.wyc.annotation.handler;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.wyc.domain.TemporaryData;
import com.wyc.service.TemporaryDataService;

public class BeforeGoodTypeHandler implements Handler{
    @Autowired
    private TemporaryDataService temporaryDataService;
    @Override
    public Object handle(HttpServletRequest httpServletRequest)
            throws Exception {
        HttpSession httpSession = httpServletRequest.getSession();
        String goodType = httpServletRequest.getParameter("good_type");
        TemporaryData temporaryData = temporaryDataService.findByMyKeyAndNameAndStatus(httpSession.getId(), "good_type" , 1);
        if(goodType!=null&&!goodType.trim().equals("")){
            if(temporaryData==null){
                temporaryData = new TemporaryData();
                temporaryData.setMykey(httpSession.getId());
                temporaryData.setName("goodType");
                temporaryData.setValue(goodType);
                temporaryDataService.add(temporaryData);
            }else{
                temporaryData.setValue(goodType);
                temporaryDataService.save(temporaryData);
            }
            httpServletRequest.setAttribute("goodType", goodType);
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
        // TODO Auto-generated method stub
        
    }

}