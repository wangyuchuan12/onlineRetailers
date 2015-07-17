package com.wyc.intercept.domain;

import java.util.HashMap;
import java.util.Map;

public class ResponseBean {
    private Map<String, Object> responseMap = new HashMap<String, Object>();

    public Map<String, Object> getResponseMap() {
        return responseMap;
    }

    public void setResponseMap(Map<String, Object> responseMap) {
        this.responseMap = responseMap;
    }
    
}
