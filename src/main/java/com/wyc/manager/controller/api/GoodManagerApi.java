package com.wyc.manager.controller.api;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wyc.defineBean.ApiResponse;
import com.wyc.domain.Good;
import com.wyc.domain.MyResource;
import com.wyc.service.GoodService;
import com.wyc.service.MyResourceService;

@RestController
public class GoodManagerApi {
    @Autowired
    private GoodService goodService;
    @Autowired
    private MyResourceService resourceService;
    private Logger logger = LoggerFactory.getLogger(GoodManagerApi.class);
    @RequestMapping("/manager/api/add_good")
    public Object addGood(HttpServletRequest servletRequest , @RequestParam("head_img")MultipartFile multipartFile){
        Good good = new Good();
        good.setId(UUID.randomUUID().toString());
        good.setAloneDiscount(Float.parseFloat(servletRequest.getParameter("alone_discount")));
        good.setAloneOriginalCost(Float.parseFloat(servletRequest.getParameter("alone_original_cost")));
        good.setFlowPrice(Float.parseFloat(servletRequest.getParameter("flow_price")));
        good.setGroupDiscount(Float.parseFloat(servletRequest.getParameter("group_discount")));
        good.setGroupOriginalCost(Float.parseFloat(servletRequest.getParameter("group_original_cost")));
       
        good.setInstruction(servletRequest.getParameter("instruction"));
        good.setMarketPrice(Float.parseFloat(servletRequest.getParameter("market_price")));
        good.setName(servletRequest.getParameter("name"));
        MyResource myResource = new MyResource();
        String resourceId = UUID.randomUUID().toString();
        myResource.setId(resourceId);
        String fileName = multipartFile.getOriginalFilename();
        myResource.setSuffix(fileName.substring(fileName.lastIndexOf(".")+1));
        myResource.setName(fileName.substring(0,fileName.lastIndexOf(".")));
        good.setHeadImg(resourceId);
        goodService.add(good);
        try {
            resourceService.addToWebpath(myResource, multipartFile.getInputStream());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new ApiResponse(ApiResponse.OK, "success");
    }
}
