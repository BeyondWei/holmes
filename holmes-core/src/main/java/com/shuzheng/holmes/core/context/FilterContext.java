package com.shuzheng.holmes.core.context;

import com.shuzheng.holmes.core.filter.HolmesFilter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 过滤器容器
 */
public class FilterContext {

    private static ConcurrentHashMap<String, HolmesFilter> contextMap = new ConcurrentHashMap<>();

    private FilterContext() {

    }


    /**
     * 过滤器注册
     */
    public static void register(HolmesFilter holmesFilter) {
        contextMap.putIfAbsent(holmesFilter.getFilterName(), holmesFilter);
    }

    /**
     * 过滤器删除
     */
    public static void remove(String holmesFilterName) {
        contextMap.remove(holmesFilterName);
    }

    /**
     * 判断过滤器是否存在
     */
    public static boolean isExist(String holmesFilterName) {
        for (String s : contextMap.keySet()) {
            if (holmesFilterName.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取过滤器
     */
    public static HolmesFilter getFilter(String holmesFilterName) {
        if (isExist(holmesFilterName)) {
            return contextMap.get(holmesFilterName);
        } else {
            throw new RuntimeException("无 `" + holmesFilterName + "` 过滤器");
        }
    }

}
