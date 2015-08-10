package com.wyc.manager.controller.api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wyc.defineBean.ApiResponse;
import com.wyc.domain.Good;
import com.wyc.domain.GoodImg;
import com.wyc.domain.MyResource;
import com.wyc.service.GoodImgService;
import com.wyc.service.GoodService;
import com.wyc.service.MyResourceService;

@RestController
public class GoodManagerApi {
    @Autowired
    private GoodService goodService;
    @Autowired
    private MyResourceService resourceService;
    @Autowired
    private GoodImgService goodImgService;
    private Logger logger = LoggerFactory.getLogger(GoodManagerApi.class);
    @RequestMapping("/manager/api/add_good")
    public Object addGood(HttpServletRequest servletRequest){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)servletRequest;
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("head_img");
        
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
        String fileName = commonsMultipartFile.getOriginalFilename();
        myResource.setSuffix(fileName.substring(fileName.lastIndexOf(".")+1));
        myResource.setName(fileName.substring(0,fileName.lastIndexOf(".")));
        good.setHeadImg(resourceId);
        goodService.add(good);
        try {
            resourceService.addToWebpath(myResource, commonsMultipartFile.getInputStream());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new ApiResponse(ApiResponse.OK, "success");
    }
    
    @RequestMapping("/manager/api/good_list")
    public Object goodList(HttpServletRequest httpServletRequest){
        Iterable<Good> goods = goodService.findAll();
        Map<String, Object> data = new HashMap<String, Object>();
        List<Object> root = new ArrayList<Object>();
        for(Good good:goods){
            root.add(good);
        }
        data.put("root", root);
        return data;
    }
    
    @RequestMapping("/manager/api/good_imgs")
    public List<Map<String, String>> goodImgs(HttpServletRequest httpServletRequest){
        String goodId = httpServletRequest.getParameter("good_id");
        System.out.println(goodId);
        Iterable<GoodImg> goodImages = goodImgService.findAllByGoodIdOrderByLevel(goodId);
        List<Map<String, String>> imgs = new ArrayList<Map<String,String>>();
        for(GoodImg goodImg:goodImages){
            Map<String, String> goodImgMap = new HashMap<String, String>();
            goodImgMap.put("id", goodImg.getId());
            MyResource myResource = resourceService.findOne(goodImg.getImgId());
            goodImgMap.put("src", myResource.getUrl());
            imgs.add(goodImgMap);
        }
        return imgs;
    }
    
    public ApiResponse updateGood(HttpServletRequest servletRequest){
        String id = servletRequest.getParameter("id");
        Good good = goodService.findOne(id);
        if(good==null){
            return new ApiResponse(ApiResponse.FORBIDDEN,"not have this record");
        }else{
            return null;
        }
    }
    
    public ApiResponse deleteGood(HttpServletRequest servletRequest){
        String id = servletRequest.getParameter("id");
        goodService.delete(id);
        return new ApiResponse(ApiResponse.OK, "success");
    }
    
}
