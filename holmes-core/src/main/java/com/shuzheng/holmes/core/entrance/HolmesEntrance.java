package com.shuzheng.holmes.core.entrance;

public interface HolmesEntrance {

    /**
     * 初始化处理
     */
    void init();

    /**
     * 数据接收
     */
    void receive(Object... object);

    /**
     * 数据接收后置处理
     */
    void postProcessing();

    /**
     * 数据接收补充处理
     */
    void supplement();
}
