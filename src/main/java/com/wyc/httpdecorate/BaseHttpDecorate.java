package com.wyc.httpdecorate;

import com.wyc.intercept.domain.MyHttpServletRequest;

public interface BaseHttpDecorate {
    public MyHttpServletRequest execute()throws Exception;
}
