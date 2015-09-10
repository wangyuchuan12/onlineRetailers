package com.wyc.annotation.handler;

import javax.servlet.http.HttpServletRequest;

public interface Handler {
    public Object handle(HttpServletRequest httpServletRequest)throws Exception;
    public Class<? extends Handler>[] extendHandlers();
}
