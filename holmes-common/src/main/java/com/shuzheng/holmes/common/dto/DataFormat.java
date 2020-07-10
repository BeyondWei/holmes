package com.shuzheng.holmes.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataFormat {

    private Map<String, String> headerMap;
    private String msg;
    private Integer id;

    public DataFormat(Map<String, String> headerMap, String msg) {
        this.headerMap = headerMap;
        this.msg = msg;
    }
}
