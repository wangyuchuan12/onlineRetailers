package com.wyc.annotation.handler;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import com.wyc.domain.Customer;
import com.wyc.domain.SystemGoodType;
import com.wyc.domain.TemporaryData;
import com.wyc.intercept.domain.MyHttpServletRequest;
import com.wyc.service.CustomerService;
import com.wyc.service.GoodTypeService;
import com.wyc.service.TemporaryDataService;
import com.wyc.wx.domain.UserInfo;

public class AfterGoodTypeHandler implements Handler{
    @Autowired
    private TemporaryDataService temporaryDataService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private GoodTypeService goodTypeService;
    @Override
    public Object handle(HttpServletRequest httpServletRequest)
            throws Exception {
        MyHttpServletRequest myHttpServletRequest = (MyHttpServletRequest)httpServletRequest;
        UserInfo userInfo = myHttpServletRequest.getUserInfo();
        HttpSession httpSession = myHttpServletRequest.getSession();
        TemporaryData goodTypeTemporaryData = temporaryDataService.findByMyKeyAndNameAndStatus(httpSession.getId(), "goodType" , 1);
        Customer customer = customerService.findByOpenId(userInfo.getOpenid());
        String goodTypeId = null;
        SystemGoodType systemGoodType = null;
        if(goodTypeTemporaryData!=null){
            goodTypeId = goodTypeTemporaryData.getValue();
            goodTypeTemporaryData.setStatus(0);
            temporaryDataService.save(goodTypeTemporaryData);
            systemGoodType = goodTypeService.findOne(goodTypeId);
            
        }
         
        if(systemGoodType == null){
            goodTypeId = customer.getDefaultGoodType();
            systemGoodType = goodTypeService.findOne(goodTypeId);
            if(systemGoodType == null){
                Iterable<SystemGoodType> goodTypeIterable = goodTypeService.findAll();
                for(SystemGoodType goodTypeEntity:goodTypeIterable){
                    if(goodTypeEntity.isDefault()){
                        goodTypeId = goodTypeEntity.getId();
                        break;
                    }
                    goodTypeId = goodTypeEntity.getId();
                }
                
            }
        }
        customer.setDefaultGoodType(goodTypeId);
        customerService.save(customer);
        System.out.println("goodTypeId1:"+goodTypeId);
        SystemGoodType goodType = null;
        if(httpServletRequest.getAttribute("goodType")==null){
            goodType = goodTypeService.findOne(goodTypeId);
            System.out.println("goodType2:"+goodType);
        }else{
            goodType = goodTypeService.findOne(httpServletRequest.getAttribute("goodType").toString());
            goodTypeId = goodType.getId();
            System.out.println("goodType3:"+goodType);
        }
        
        
        
        httpServletRequest.setAttribute("goodType", goodTypeId);
        httpServletRequest.setAttribute("typeTitle", goodType.getTitle());
        httpServletRequest.setAttribute("typeName", goodType.getName());
        httpServletRequest.setAttribute("typeImg", goodType.getImg());
        return null;
    }

    @Override
    public Class<? extends Handler>[] extendHandlers() {
        return null;
    }

    @Override
    public void setAnnotation(Annotation annotation) {
        // TODO Auto-generated method stub
        
    }

}
