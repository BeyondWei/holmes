package com.shuzheng.holmes.server.dto;

import com.shuzheng.holmes.core.context.ConfigContext;
import lombok.Data;

@Data
public class FilterDto {
    private String holmesFilterName;
    private String className;
    // 编译时的依赖包的地址
    private String classPath;
    // java文件的地址
    private String javaPath;
    // 配置信息
    private String configJson;
    private String msg;
}
