package com.wyc.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wyc.annotation.UserInfoFromWebAnnotation;
import com.wyc.domain.TempGroupOrder;
import com.wyc.service.TempGroupOrderService;

@RestController
public class OrderApi {
    @Autowired
    private TempGroupOrderService tempGroupOrderService;
    @RequestMapping(value = "/api/set_good_style")
    @UserInfoFromWebAnnotation
    public Object setTempGroupOrderOfGoodStyle(HttpServletRequest httpServletRequest){
        String tempGroupOrderId = httpServletRequest.getParameter("temp_group_order_id");
        String goodStyleId = httpServletRequest.getParameter("good_style_id");
        TempGroupOrder tempGroupOrder = tempGroupOrderService.findOne(tempGroupOrderId);
        tempGroupOrder.setGoodStyleId(goodStyleId);
        tempGroupOrderService.save(tempGroupOrder);
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("result", true);
        return response;
    }
}
