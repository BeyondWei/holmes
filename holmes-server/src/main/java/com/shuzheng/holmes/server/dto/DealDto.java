package com.shuzheng.holmes.server.dto;

import lombok.Data;

@Data
public class DealDto {
    private String holmesDealName;
    private String className;
    // 编译时的依赖包的地址
    private String classPath;
    // java文件的地址
    private String javaPath;

}
