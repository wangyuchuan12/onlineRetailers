package com.wyc.manager.controller.api;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.wyc.defineBean.ApiResponse;
import com.wyc.domain.Good;
import com.wyc.domain.GoodDistribution;
import com.wyc.domain.GoodImg;
import com.wyc.domain.MyResource;
import com.wyc.domain.Source;
import com.wyc.service.CityService;
import com.wyc.service.GoodDistributionService;
import com.wyc.service.GoodImgService;
import com.wyc.service.GoodService;
import com.wyc.service.MyResourceService;
import com.wyc.service.SourceService;

@RestController
public class GoodManagerApi {
    @Autowired
    private GoodService goodService;
    @Autowired
    private MyResourceService resourceService;
    @Autowired
    private GoodImgService goodImgService;
    @Autowired
    private SourceService sourceService;
    @Autowired
    private GoodDistributionService goodDistributionService;
    @Autowired
    private CityService cityService;
    
    
  //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "0aPSy8Q-S-e7eb7OsYc6xjRO2PjLivG774Jp7tI5";
    String SECRET_KEY = "nL8W-aLnv1hxZeZECQRmsln15kO8F-EvmMbltCBM";
    //要上传的空间
    String bucketname = "picture";
    
  //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    UploadManager uploadManager = new UploadManager();
    private Logger logger = LoggerFactory.getLogger(GoodManagerApi.class);
    @RequestMapping("/manager/api/add_good")
    public Object addGood(MultipartHttpServletRequest servletRequest){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)servletRequest;
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("head_img");
        
        Good good = new Good();
        good.setAloneDiscount(Float.parseFloat(servletRequest.getParameter("alone_discount")));
        good.setAloneOriginalCost(Float.parseFloat(servletRequest.getParameter("alone_original_cost")));
        good.setFlowPrice(Float.parseFloat(servletRequest.getParameter("flow_price")));
        good.setGroupDiscount(Float.parseFloat(servletRequest.getParameter("group_discount")));
        good.setGroupOriginalCost(Float.parseFloat(servletRequest.getParameter("group_original_cost")));
        good.setGroupNum(Integer.parseInt(servletRequest.getParameter("group_num")));
        good.setStatus(Integer.parseInt(servletRequest.getParameter("status")));
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
        good.setCouponCost(Integer.parseInt(servletRequest.getParameter("coupon_cost")));
        good.setGroupNum(Integer.parseInt(servletRequest.getParameter("group_num")));
        good.setInstruction(servletRequest.getParameter("instruction"));
        good.setMarketPrice(Float.parseFloat(servletRequest.getParameter("market_price")));
        good.setName(servletRequest.getParameter("name"));
        good.setStatus(Integer.parseInt(servletRequest.getParameter("status")));
        MyResource myResource = new MyResource();
        String resourceId = UUID.randomUUID().toString();
        myResource.setId(resourceId);
        if(commonsMultipartFile!=null&&commonsMultipartFile.getOriginalFilename()!=null&&!commonsMultipartFile.getOriginalFilename().trim().equals("")){
            
            String fileName = commonsMultipartFile.getOriginalFilename();
            myResource.setSuffix(fileName.substring(fileName.lastIndexOf(".")+1));
            myResource.setName(fileName.substring(0,fileName.lastIndexOf(".")));
            good.setHeadImg(resourceId);
            
        }
        goodService.save(good);
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
    
    @RequestMapping("/manager/api/get_source")
    public Source getSource(HttpServletRequest httpServletRequest){
        String good_id = httpServletRequest.getParameter("good_id");
        Good good = goodService.findOne(good_id);
        if(good==null||good.getSourceId()==null||good.getSourceId().trim().equals("")){
            return null;
        }else{
            String sourceId = good.getSourceId();
            Source source = sourceService.findOne(sourceId);
            return source;
        }
    }
    
    @RequestMapping("/manager/api/get_distribution")
    public Object getProvinceDistribution(HttpServletRequest httpServletRequest){
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        for(Entry<String, String[]> entry:parameterMap.entrySet()){
            logger.debug("{}:{}",entry.getKey(),entry.getValue()[0]);
        }
        String goodId = httpServletRequest.getParameter("good_id");
        String node = httpServletRequest.getParameter("node");
        String parentCity = null;
        if(node.equals("root")){
            parentCity = "root";
        }else{
            GoodDistribution goodDistribution = goodDistributionService.findOne(node);
            parentCity = goodDistribution.getCity();
        }
        logger.debug("parentCity is {}",parentCity);
        Iterable<GoodDistribution> provinceIterable = goodDistributionService.findAllByGoodIdAndParentCity(goodId, parentCity);
        List<Map<String, Object>> citys = new ArrayList<Map<String,Object>>();
        for(GoodDistribution provinceDistribution:provinceIterable){
            Map<String, Object> responseProvince = new HashMap<String, Object>();
            responseProvince.put("text", provinceDistribution.getCityName());
            responseProvince.put("id", provinceDistribution.getId());
            responseProvince.put("parentCity", provinceDistribution.getParentCity());
            responseProvince.put("city", provinceDistribution.getCity());
            responseProvince.put("leaf", false);
            citys.add(responseProvince);
        }
        return citys;
    }
    
    @RequestMapping("/manager/api/save_source")
    public Object saveSource(HttpServletRequest httpServletRequest){
        String id = httpServletRequest.getParameter("id");
        String price = httpServletRequest.getParameter("price");
        String type = httpServletRequest.getParameter("type");
        String url = httpServletRequest.getParameter("url");
        String address = httpServletRequest.getParameter("address");
        String name = httpServletRequest.getParameter("name");
        String goodId = httpServletRequest.getParameter("good_id");
        Source source = null;
        if(id!=null&&!id.trim().equals("")){
            source = sourceService.findOne(id);
            source.setPrice(Float.parseFloat(price));
            source.setAddress(address);
            source.setName(name);
            source.setType(Integer.parseInt(type));
            source.setUrl(url);
            sourceService.save(source);
            return "{'success':true,'type':0}";
        }else{
            source = new Source();
            source.setPrice(Float.parseFloat(price));
            source.setAddress(address);
            source.setName(httpServletRequest.getParameter("name"));
            source.setType(Integer.parseInt(type));
            source.setUrl(url);
            source.setName(name);
            source = sourceService.add(source);
            Good good = goodService.findOne(goodId);
            good.setSourceId(source.getId());
            goodService.save(good);
            return "{'success':true,'type':1}";
        }
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
    
    private String upload(String filePath , String key) throws IOException{
        String token = auth.uploadToken(bucketname,key);
        //调用put方法上传
        Response res = uploadManager.put(filePath, key, token);
        //打印返回的信息
        return res.bodyString(); 

    }
    @RequestMapping("/manager/api/sync_data")
    public Object synchData(HttpServletRequest httpServletRequest){
        Iterable<MyResource> myResources = resourceService.findAll();
        HashMap<String, Object> response = new HashMap<String, Object>();
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> successIds = new ArrayList<String>();
        List<String> errorIds = new ArrayList<String>();
        for(MyResource myResource:myResources){
            try {
                String returnBody = upload(myResource.getSystemUrl(), myResource.getId());
                HashMap<String, String> hashMap = objectMapper.readValue(returnBody, HashMap.class);
                myResource.setUrl("http://7xlw44.com1.z0.glb.clouddn.com/"+hashMap.get("key"));
                resourceService.save(myResource);
                successIds.add(myResource.getId());
            } catch (Exception e) {
                logger.error("has error:{}",e);
                errorIds.add(myResource.getId());
            }
        }
        response.put("successIds", successIds);
        response.put("errorIds", errorIds);
        return response;
    }
    
    @RequestMapping("/manager/api/good_delete")
    public Object deleteGood(HttpServletRequest servletRequest){
        String id = servletRequest.getParameter("id");
        goodService.delete(id);
        return "{'success':true,'items':{'servername':'测试一区','result':1}}";
    } 
}
