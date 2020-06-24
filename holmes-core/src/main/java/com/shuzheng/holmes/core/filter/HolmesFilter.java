package com.shuzheng.holmes.core.filter;

import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.enums.FilterTypeEnums;

public interface HolmesFilter {

    /**
     * 过滤器类型
     */
    FilterTypeEnums getFilterType();

    /**
     * 过滤器名称
     */
    String getFilterName();

    /**
     * 过滤器实现
     */
    Object filter(Object msg);

}

