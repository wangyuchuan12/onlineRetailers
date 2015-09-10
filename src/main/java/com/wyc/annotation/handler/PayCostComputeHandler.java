package com.wyc.annotation.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PayCostComputeHandler implements Handler{
    final static Logger logger = LoggerFactory.getLogger(PayCostComputeHandler.class);
    @Override
    public Object handle(HttpServletRequest httpServletRequest) {
        logger.debug("...............................");
        return null;
    }
   
}
