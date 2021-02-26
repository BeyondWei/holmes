package com.shuzheng.holmes.core.context;

import com.shuzheng.holmes.core.deal.HolmesDeal;
import com.shuzheng.holmes.core.filter.HolmesFilter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理器容器
 */
public class DealContext {

    private static ConcurrentHashMap<String, HolmesDeal> contextMap = new ConcurrentHashMap<>();

    private DealContext() {

    }


    /**
     * 处理器注册
     */
    public static void register(HolmesDeal holmesDeal) {
        contextMap.putIfAbsent(holmesDeal.getDealName(), holmesDeal);
    }

    /**
     * 处理器删除
     */
    public static void remove(String holmesDealName) {
        contextMap.remove(holmesDealName);
    }

    /**
     * 判断处理器是否存在
     */
    public static boolean isExist(String holmesDealName) {
        return contextMap.keySet().contains(holmesDealName);
    }

    /**
     * 获取处理器
     */
    public static HolmesDeal getDeal(String holmesDealName) {
        if (isExist(holmesDealName)) {
            return contextMap.get(holmesDealName);
        } else {
            throw new RuntimeException("无 `" + holmesDealName + "` 处理器");
        }
    }

}
