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
        if(goodTypeTemporaryData!=null){
            goodTypeId = goodTypeTemporaryData.getValue();
            goodTypeTemporaryData.setStatus(0);
            temporaryDataService.save(goodTypeTemporaryData);
            customer.setDefaultGoodType(goodTypeId);
            customerService.save(customer);
        }
        if(goodTypeId==null||goodTypeId.equals("")){
            goodTypeId = customer.getDefaultGoodType();
            if(goodTypeId==null||goodTypeId.trim().equals("")){
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
        SystemGoodType goodType = null;
        if(httpServletRequest.getAttribute("goodType")==null){
            goodType = goodTypeService.findOne(goodTypeId);
        }else{
            goodType = goodTypeService.findOne(httpServletRequest.getAttribute("goodType").toString());
            goodTypeId = goodType.getId();
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
