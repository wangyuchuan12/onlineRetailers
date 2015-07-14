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
    private String data;

    public static ApiResponse Fail(String dataObject) {
        return ApiResponse1(ApiResponse.FORBIDDEN, dataObject, false);
    }

    private static ApiResponse ApiResponse1(int forbidden2, String dataObject,
            boolean b) {
        return new ApiResponse(forbidden2, dataObject, false);
    }

    public static ApiResponse OK(String dataObject) {
        return new ApiResponse(ApiResponse.OK, dataObject, false);
    }

    public static ApiResponse OK(String dataObject, Boolean encrypt) {
        return new ApiResponse(ApiResponse.OK, dataObject, true);
    }

    /**
     * @param encrypted
     *            数据是否已加密，若未加密则自动加密
     * @author Jack
     * @return
     */
    public ApiResponse(int statusCode, String dataObject, Boolean encrypted) {
        this.code = statusCode;
        this.data=dataObject;
    }

    public ApiResponse(int statusCode, String dataObject) {
        this(statusCode, dataObject, false);
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

}
