package com.shuzheng.holmes.common.exception;

/**
 * Created by yscredit on 2019/4/3.
 */
public enum BizExceptionType {

    APPKEY("01"),SIGN("02"),INTERCODE("03"), REQUEST_TIME("04"),APP("05"),RIGHT("06"),APPLY("07")
    ,ACCESS("08"),DATACOUNT("09"),INTERFACE("10"),INVOKE("11"),CHEAT("12"),SYSTEM("99"),
    BASICLEVEL("101"),SECONDARYPLAT("102"),IDENTITY_AUTH("303");

    private String code;


    BizExceptionType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
