package com.shuzheng.holmes.server.task;

import com.shuzheng.holmes.common.dto.FlumeData;
import com.shuzheng.holmes.common.utils.ClassloadUtils;
import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.context.FilterContext;
import com.shuzheng.holmes.core.enums.FilterTypeEnums;
import com.shuzheng.holmes.core.filter.HolmesFilter;
import com.shuzheng.holmes.core.filter.HolmesFilterAbstract;
import com.shuzheng.holmes.core.filter.HolmesFilterFactory;

public class FilterTask implements Runnable {

    private FlumeData flumeData;

    public FilterTask(FlumeData flumeData) {
        this.flumeData = flumeData;
    }

    @Override
    public void run() {
        // todo 根据数据类型从数据库获取所有相关的过滤规则

        // TODO 加载class
        Class<HolmesFilterAbstract> aClass = (Class<HolmesFilterAbstract>) ClassloadUtils.getClassloadUtils().classLoadFromClassFile("com.shuzheng.holmes.core.filter.TestFilter", "C:\\Users\\WIN10\\Desktop\\TestFilter.class");
        // 生产过滤器
        ConfigContext configContext = new ConfigContext();
        configContext.put("regular", "\\d{15}(\\d{2}[0-9xX])?");
        HolmesFilterAbstract factory = HolmesFilterFactory.factory(aClass, configContext, FilterTypeEnums.JAVA);
        factory.register();
        // 执行过滤器
        HolmesFilter filter = FilterContext.getFilter("com.shuzheng.holmes.core.filter.TestFilter");
        Object dasdsds = filter.filter("dasdsds");
        // todo 匹配相应的结果判断是否需要插入告警信息（设计想法中，处理也是可以分多种类型，方式与过滤方式类似）
    }

    public static void main(String[] args) {
        // 生产过滤器
        if (!FilterContext.isExist("com.shuzheng.holmes.core.filter.TestFilter")) {
            Class<HolmesFilterAbstract> aClass = (Class<HolmesFilterAbstract>) ClassloadUtils.getClassloadUtils().classLoadFromJavaFile(
                    "com.shuzheng.holmes.core.filter.TestFilter",
                    "C:\\Users\\WIN10\\Desktop\\jar\\TestFilter.java",
                    "C:\\Users\\WIN10\\Desktop\\jar\\holmes-core-1.0-SNAPSHOT.jar;C:\\Users\\WIN10\\Desktop\\jar\\fastjson-1.2.61.jar");
            ConfigContext configContext = new ConfigContext();
            configContext.put("regular", "\\d{15}(\\d{2}[0-9xX])?");
            HolmesFilterAbstract factory = HolmesFilterFactory.factory(aClass, configContext, FilterTypeEnums.JAVA);
            factory.register();
        }
        // 执行过滤器
        HolmesFilter filter = FilterContext.getFilter("com.shuzheng.holmes.core.filter.TestFilter");
        Object dasdsds = filter.filter("{\"id\":\"1\",\"name\":\"beyond\"}");
    }


}
