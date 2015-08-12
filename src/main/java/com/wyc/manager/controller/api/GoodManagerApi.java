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
    public Object addGood(MultipartHttpServletRequest servletRequest){
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
        return "{'success':true,'items':{'servername':'测试一区','result':1}}";
    }
    
    
    @RequestMapping("/manager/api/update_good")
    public Object updateGood(MultipartHttpServletRequest servletRequest){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)servletRequest;
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("head_img");
        String id = servletRequest.getParameter("id");
        Good good = goodService.findOne(id);
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
        if(commonsMultipartFile!=null&&commonsMultipartFile.getOriginalFilename()!=null&&!commonsMultipartFile.getOriginalFilename().trim().equals("")){
            
            String fileName = commonsMultipartFile.getOriginalFilename();
            myResource.setSuffix(fileName.substring(fileName.lastIndexOf(".")+1));
            myResource.setName(fileName.substring(0,fileName.lastIndexOf(".")));
            good.setHeadImg(resourceId);
            
        }
        
        goodService.add(good);
        try {
            resourceService.addToWebpath(myResource, commonsMultipartFile.getInputStream());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "{'success':true,'items':{'servername':'测试一区','result':1}}";
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
        logger.debug("data is {}",data);
        return data;
    }
    
    @RequestMapping("/manager/api/good_imgs")
    public List<Map<String, String>> goodImgs(HttpServletRequest httpServletRequest){
        String goodId = httpServletRequest.getParameter("good_id");
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
    
    @RequestMapping("/manager/api/add_img")
    public Object addGoodImg(MultipartHttpServletRequest servletRequest){
        String goodId = servletRequest.getParameter("good_id");
        GoodImg goodImg = new GoodImg();
        goodImg.setGoodId(goodId);
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) servletRequest.getFile("img");
        MyResource myResource = new MyResource();
        String resourceId = UUID.randomUUID().toString();
        myResource.setId(resourceId);
        if(commonsMultipartFile!=null&&commonsMultipartFile.getOriginalFilename()!=null&&!commonsMultipartFile.getOriginalFilename().trim().equals("")){
            
            String fileName = commonsMultipartFile.getOriginalFilename();
            myResource.setSuffix(fileName.substring(fileName.lastIndexOf(".")+1));
            myResource.setName(fileName.substring(0,fileName.lastIndexOf(".")));
            goodImg.setImgId(resourceId);
            
        }
        try {
            resourceService.addToWebpath(myResource, commonsMultipartFile.getInputStream());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return "{'success':false}";
        }
        resourceService.add(myResource);
        goodImgService.add(goodImg);
        return "{'success':true}";
    }
    
    @RequestMapping("/manager/api/delete_img")
    public Object deleteGoodImg(HttpServletRequest servletRequest){
        String img_id = servletRequest.getParameter("img_id");
        goodImgService.delete(img_id);
        return "{'success':true}";
    }
    @RequestMapping("/manager/api/good_info")
    public Object goodInfo(HttpServletRequest httpServletRequest){
        String id = httpServletRequest.getParameter("id");
        Good good = goodService.findOne(id);
        return good;
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
    
    @RequestMapping("/manager/api/good_delete")
    public Object deleteGood(HttpServletRequest servletRequest){
        String id = servletRequest.getParameter("id");
        goodService.delete(id);
        return "{'success':true,'items':{'servername':'测试一区','result':1}}";
    }
    
}
