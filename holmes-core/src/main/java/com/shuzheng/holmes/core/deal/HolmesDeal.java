package com.shuzheng.holmes.core.deal;

import com.shuzheng.holmes.core.enums.DealTypeEnums;

public interface HolmesDeal {

    /**
     * 处理器类型
     */
    DealTypeEnums getDealType();

    /**
     * 处理器名称
     */
    String getDealName();

    /**
     * 处理器实现
     */
    void deal(Object msg);
}
