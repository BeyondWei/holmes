package com.shuzheng.holmes.server.task;

import com.alibaba.fastjson.JSONObject;
import com.shuzheng.holmes.common.Constants;
import com.shuzheng.holmes.common.dto.DataFormat;
import com.shuzheng.holmes.common.entity.THsDealRulesInfo;
import com.shuzheng.holmes.common.entity.THsFilterRulesGroup;
import com.shuzheng.holmes.common.entity.THsFilterRulesInfo;
import com.shuzheng.holmes.common.utils.ClassloadUtils;
import com.shuzheng.holmes.common.utils.SpringUtil;
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
import com.shuzheng.holmes.server.dto.DealDto;
import com.shuzheng.holmes.server.dto.FilterDto;
import com.shuzheng.holmes.service.bussiness.BusHolmesServerService;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FilterTask implements Runnable {
    private DataFormat dataFormat;
    private AtomicInteger atomicInteger;

    public static HashMap<String, String> results_map = new HashMap<>();

    public FilterTask(DataFormat dataFormat, AtomicInteger atomicInteger) {
        this.dataFormat = dataFormat;
        this.atomicInteger = atomicInteger;
    }

    @Override
    public void run() {
        BusHolmesServerService busHolmesServerService = SpringUtil.getBean(BusHolmesServerService.class);
        String logUuid = dataFormat.getHeaderMap().get(Constants.HOLMES_LOG_UUID);
        String projectUuid = dataFormat.getHeaderMap().get(Constants.HOLMES_PROJECT_UUID);
        results_map.put(projectUuid + "-" + logUuid, dataFormat.getMsg());
        // 获取不同的过滤组
        List<THsFilterRulesGroup> filterRulesGroupByProjectUuid = busHolmesServerService.getFilterRulesGroupByProjectUuid(projectUuid);
        // 分别执行不同的过滤组
        filterRulesGroupByProjectUuid.forEach(filterRulesGroup -> {

            new Thread(() -> {
                List<THsFilterRulesInfo> tHsFilterRulesInfoByFilterGroupUuid = busHolmesServerService.getTHsFilterRulesInfoByFilterGroupUuidAndlogUuid(filterRulesGroup.getUuid(), logUuid);

                // 按顺序执行
                Object res = dataFormat.getMsg();
                for (THsFilterRulesInfo tHsFilterRulesInfo : tHsFilterRulesInfoByFilterGroupUuid) {
                    // filter 执行
                    res = todoFilter(res, tHsFilterRulesInfo);

                    // deal 执行
                    String dealGroupUuid = tHsFilterRulesInfo.getDealGroupUuid();
                    List<THsDealRulesInfo> tHsDealRulesInfoByDealGroupUuid = busHolmesServerService.getTHsDealRulesInfoByDealGroupUuid(dealGroupUuid);
                    Object finalRes = res;
                    tHsDealRulesInfoByDealGroupUuid.forEach(tHsDealRulesInfo -> {
                        todoDeal(finalRes, tHsDealRulesInfo);
                    });
                }
            }).start();
        });

//        System.out.println(flumeData.getMsg() + "处理结束");
//        tHsKafkaLogInfoService.deleteById(flumeData.getId());
//        DataOffset.deleteFile("kafka", record.topic() + "-" + record.partition() + "-" + record.offset());
        atomicInteger.decrementAndGet();
        HttpEntranceTask.atomicInteger.incrementAndGet();
    }


    /**
     * 过滤处理
     */
    private Object todoFilter(Object msg, THsFilterRulesInfo tHsFilterRulesInfo) {
        FilterDto filterDto = JSONObject.parseObject(tHsFilterRulesInfo.getContext(), FilterDto.class);
        String filterName = tHsFilterRulesInfo.getUuid() + "-" + filterDto.getHolmesFilterName();
        ConfigContext configContext = new ConfigContext();
        configContext.getJsonProperties(tHsFilterRulesInfo.getConfig());
        // 生产过滤器
        if (!FilterContext.isExist(filterName)) {
            Class<HolmesFilterAbstract> aClass = (Class<HolmesFilterAbstract>) ClassloadUtils.getClassloadUtils().tryGetClass(filterDto.getClassName());
            if (aClass == null) {
                aClass = (Class<HolmesFilterAbstract>) ClassloadUtils.getClassloadUtils().classLoadFromJavaFile(
                        filterDto.getClassName(),
                        filterDto.getJavaPath(),
                        filterDto.getClassPath());
            }
            HolmesFilterFactory.createAndRegister(aClass, filterName, configContext, FilterTypeEnums.valueOf(tHsFilterRulesInfo.getType()));
        }
        // 执行过滤器
        HolmesFilterAbstract holmesFilter = (HolmesFilterAbstract) FilterContext.getFilter(filterName);
        // 自动更新
        if (!holmesFilter.getConfigContext().equals(configContext)) {
            HolmesFilterFactory.updateFilter(filterName, configContext);
        }
        return holmesFilter.run(msg);
    }

    /**
     * 执行处理
     */
    private void todoDeal(Object object, THsDealRulesInfo tHsDealRulesInfo) {
        DealDto dealDto = JSONObject.parseObject(tHsDealRulesInfo.getContext(), DealDto.class);
        String dealName = tHsDealRulesInfo.getUuid() + "-" + dealDto.getHolmesDealName();
        ConfigContext configContext = new ConfigContext();
        configContext.getJsonProperties(tHsDealRulesInfo.getConfig());
        if (!DealContext.isExist(dealName)) {
            Class<HolmesDealAbstract> aClass = (Class<HolmesDealAbstract>) ClassloadUtils.getClassloadUtils().tryGetClass(dealDto.getClassName());
            if (aClass == null) {
                aClass = (Class<HolmesDealAbstract>) ClassloadUtils.getClassloadUtils().classLoadFromJavaFile(
                        dealDto.getClassName(),
                        dealDto.getJavaPath(),
                        dealDto.getClassPath());
            }
            HolmesDealFactory.createAndRegister(aClass, dealName, configContext, DealTypeEnums.valueOf(tHsDealRulesInfo.getType()));
        }
        // 执行处理器
        HolmesDealAbstract dealAbstract = (HolmesDealAbstract) DealContext.getDeal(dealName);
        // 自动更新
        if (!dealAbstract.getConfigContext().equals(configContext)) {
            HolmesDealFactory.updateDeal(dealName, configContext);
        }
        dealAbstract.run(object);
    }

}
