package com.shuzheng.holmes.common.dto;

import lombok.Data;

import java.util.Map;

@Data
public class FlumeData {

    private Map<String, String> headerMap;
    private String msg;
}
