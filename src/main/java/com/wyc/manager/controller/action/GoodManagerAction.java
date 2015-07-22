package com.wyc.manager.controller.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyc.config.AppConfig;

@Controller
public class GoodManagerAction {
    final static Logger logger = LoggerFactory.getLogger(GoodManagerAction.class);
    public GoodManagerAction() {
        logger.debug("...........................GoodManagerAction");
        System.out.println("...........................GoodManagerAction");
    }
    @RequestMapping("/manager/good_add")
    public String managerGoodAdd(){
        logger.debug("managerGoodAdd.......................");
        return "manager/GoodAdd";
    }
    
    @RequestMapping("/manager/good_list")
    public String managerGoodList(){
        logger.debug("managerGoodList.......................");
        return "manager/GoodList";
    }
<<<<<<< HEAD
    @RequestMapping("/manager/good_list1")
    public String managerGoodImg(){
    	return "manager/GoodList1";
=======
    @RequestMapping("/manager/GoodImg")
    public String managerGoodImg(){
        return "manager/GoodImg";
>>>>>>> 967872a26d7e8b48bf29420a3fdbf781f79d71f3
    }
}
