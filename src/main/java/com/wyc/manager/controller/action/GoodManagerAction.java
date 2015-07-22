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
    
}
