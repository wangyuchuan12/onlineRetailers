package com.wyc.defineBean;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ApiResponse implements Serializable {
    private static final long serialVersionUID = -1;

    @JsonIgnore
    final static public int OK = 200;
    @JsonIgnore
    final static public int NOT_FOUND = 404;
    @JsonIgnore
    final static public int FORBIDDEN = 403;
    @JsonIgnore
    final static public String error = "error request";
    @JsonIgnore
    final static Logger logger = LoggerFactory.getLogger(ApiResponse.class);
    private int code;
    private Object data;
    public ApiResponse(int code , Object dataObject) {
        this.code = code;
        this.data = dataObject;
    }
    public static ApiResponse Fail(Object dataObject) {
        return new ApiResponse(ApiResponse.FORBIDDEN, dataObject);
    }
    
    public static ApiResponse OK(Object dataObject) {
        return new ApiResponse(ApiResponse.OK, dataObject);
    }


    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

}
