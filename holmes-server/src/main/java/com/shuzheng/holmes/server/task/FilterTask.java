package com.shuzheng.holmes.server.task;

import com.shuzheng.holmes.common.dto.FlumeData;
import com.shuzheng.holmes.common.utils.ClassloadUtils;
import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.context.DealContext;
import com.shuzheng.holmes.core.context.FilterContext;
import com.shuzheng.holmes.core.deal.HolmesDeal;
import com.shuzheng.holmes.core.deal.HolmesDealAbstract;
import com.shuzheng.holmes.core.deal.HolmesDealFactory;
import com.shuzheng.holmes.core.enums.DealTypeEnums;
import com.shuzheng.holmes.core.enums.FilterTypeEnums;
import com.shuzheng.holmes.core.filter.HolmesFilter;
import com.shuzheng.holmes.core.filter.HolmesFilterAbstract;
import com.shuzheng.holmes.core.filter.HolmesFilterFactory;
import com.shuzheng.holmes.core.filter.RegularFilter;

public class FilterTask implements Runnable {

    private FlumeData flumeData;

    public FilterTask(FlumeData flumeData) {
        this.flumeData = flumeData;
    }

    @Override
    public void run() {
        // todo 根据数据类型从数据库获取所有相关的过滤规则

        // TODO 加载class
        testFilter(flumeData);
        regularFilter(flumeData);
    }

    /**
     * 外部处理
     * @param flumeData
     */
    public void testFilter(FlumeData flumeData) {
        // 生产过滤器
        if (!FilterContext.isExist("TestFilter")) {
            Class<HolmesFilterAbstract> aClass = (Class<HolmesFilterAbstract>) ClassloadUtils.getClassloadUtils().tryGetClass("com.shuzheng.holmes.core.filter.TestFilter");
            if (aClass == null) {
                aClass = (Class<HolmesFilterAbstract>) ClassloadUtils.getClassloadUtils().classLoadFromJavaFile(
                        "com.shuzheng.holmes.core.filter.TestFilter",
                        "C:\\Users\\WIN10\\Desktop\\jar\\TestFilter.java",
                        "C:\\Users\\WIN10\\Desktop\\jar\\holmes-core-1.0-SNAPSHOT.jar;C:\\Users\\WIN10\\Desktop\\jar\\fastjson-1.2.61.jar");
            }
            ConfigContext configContext = new ConfigContext();
            configContext.put("regular", "\\d{15}(\\d{2}[0-9xX])?");
            HolmesFilterFactory.createAndRegister(aClass, "TestFilter", configContext, FilterTypeEnums.JAVA);

        }
        // 执行过滤器
        HolmesFilter filter = FilterContext.getFilter("TestFilter");
        Object dasdsds = filter.filter("{\"id\":\"1\",\"name\":\"" + flumeData.getMsg() + "\"}");
        // 执行处理器
        if ((boolean)dasdsds){
            if (!DealContext.isExist("AlertDeal")) {
                ConfigContext configContext = new ConfigContext();
                configContext.put("regular", "\\d{15}(\\d{2}[0-9xX])?");
                Class<HolmesDealAbstract> aClass = (Class<HolmesDealAbstract>) ClassloadUtils.getClassloadUtils().tryGetClass("com.shuzheng.holmes.core.deal.AlertDeal");

                HolmesDealFactory.createAndRegister(aClass, "AlertDeal", configContext, DealTypeEnums.JAVA);
            }
            HolmesDeal alertDeal = DealContext.getDeal("AlertDeal");
            alertDeal.deal(flumeData.getMsg());
        }
    }

    /**
     * 内置处理
     * @param flumeData
     */
    public void regularFilter(FlumeData flumeData) {
        if (!FilterContext.isExist("RegularFilter")) {
            ConfigContext configContext = new ConfigContext();
            configContext.put("regular", "\\d{15}(\\d{2}[0-9xX])?");
            Class<HolmesFilterAbstract> aClass = (Class<HolmesFilterAbstract>) ClassloadUtils.getClassloadUtils().tryGetClass("com.shuzheng.holmes.core.filter.RegularFilter");

            HolmesFilterFactory.createAndRegister(aClass, "RegularFilter", configContext, FilterTypeEnums.JAVA);
        }
        HolmesFilter filter = FilterContext.getFilter("RegularFilter");
        Object dasdsds = filter.filter(flumeData.getMsg());
        // 执行处理器
        System.out.println(dasdsds);
    }


}
