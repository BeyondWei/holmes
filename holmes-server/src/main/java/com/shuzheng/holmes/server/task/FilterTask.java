package com.shuzheng.holmes.server.task;

import com.alibaba.fastjson.JSONObject;
import com.shuzheng.holmes.common.Constants;
import com.shuzheng.holmes.common.dto.FlumeData;
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
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.text.SimpleDateFormat;
import java.util.List;

public class FilterTask implements Runnable {
    private ConsumerRecord<Integer, String> record;
    private FlumeData flumeData;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public FilterTask(FlumeData flumeData, ConsumerRecord<Integer, String> record) {
        this.record = record;
        this.flumeData = flumeData;
    }

    public FilterTask(FlumeData flumeData) {
        this.flumeData = flumeData;
    }

    @Override
    public void run() {
        BusHolmesServerService busHolmesServerService = SpringUtil.getBean(BusHolmesServerService.class);
        String logUuid = flumeData.getHeaderMap().get(Constants.FLUME_LOG_UUID);
        String projectUuid = flumeData.getHeaderMap().get(Constants.FLUME_PROJECT_UUID);

        // 获取不同的过滤组
        List<THsFilterRulesGroup> filterRulesGroupByProjectUuid = busHolmesServerService.getFilterRulesGroupByProjectUuid(projectUuid);
        // 分别执行不同的过滤组
        filterRulesGroupByProjectUuid.forEach(filterRulesGroup -> {
            List<THsFilterRulesInfo> tHsFilterRulesInfoByFilterGroupUuid = busHolmesServerService.getTHsFilterRulesInfoByFilterGroupUuidAndlogUuid(filterRulesGroup.getUuid(), logUuid);

            // 按顺序执行
            Object res = flumeData.getMsg();
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
        });

//        System.out.println(flumeData.getMsg() + "处理结束");
//        tHsKafkaLogInfoService.deleteById(flumeData.getId());
//        DataOffset.deleteFile("kafka", record.topic() + "-" + record.partition() + "-" + record.offset());
        KafkaFlumeCustomerTask.COUNT.decrementAndGet();
    }


    /**
     * 过滤处理
     */
    private Object todoFilter(Object msg, THsFilterRulesInfo tHsFilterRulesInfo) {
        FilterDto filterDto = JSONObject.parseObject(tHsFilterRulesInfo.getContext(), FilterDto.class);
        String filterName = tHsFilterRulesInfo.getUuid() + "-" + filterDto.getHolmesFilterName();
        // 生产过滤器
        if (!FilterContext.isExist(filterName)) {
            Class<HolmesFilterAbstract> aClass = (Class<HolmesFilterAbstract>) ClassloadUtils.getClassloadUtils().tryGetClass(filterDto.getClassName());
            if (aClass == null) {
                aClass = (Class<HolmesFilterAbstract>) ClassloadUtils.getClassloadUtils().classLoadFromJavaFile(
                        filterDto.getClassName(),
                        filterDto.getJavaPath(),
                        filterDto.getClassPath());
            }
            ConfigContext configContext = new ConfigContext();
            configContext.getJsonProperties(tHsFilterRulesInfo.getConfig());
            HolmesFilterFactory.createAndRegister(aClass, filterName, configContext, FilterTypeEnums.valueOf(tHsFilterRulesInfo.getType()));
        }
        // 执行过滤器
        HolmesFilter holmesFilter = FilterContext.getFilter(filterName);
        return holmesFilter.run(msg);
    }

    /**
     * 执行处理
     */
    private void todoDeal(Object object, THsDealRulesInfo tHsDealRulesInfo) {
        DealDto dealDto = JSONObject.parseObject(tHsDealRulesInfo.getContext(), DealDto.class);
        String dealName = tHsDealRulesInfo.getUuid() + "-" + dealDto.getHolmesDealName();
        if (!DealContext.isExist(dealName)) {
            Class<HolmesDealAbstract> aClass = (Class<HolmesDealAbstract>) ClassloadUtils.getClassloadUtils().tryGetClass(dealDto.getClassName());
            if (aClass == null) {
                aClass = (Class<HolmesDealAbstract>) ClassloadUtils.getClassloadUtils().classLoadFromJavaFile(
                        dealDto.getClassName(),
                        dealDto.getJavaPath(),
                        dealDto.getClassPath());
            }
            ConfigContext configContext = new ConfigContext();
            configContext.getJsonProperties(tHsDealRulesInfo.getConfig());
            HolmesDealFactory.createAndRegister(aClass, dealName, configContext, DealTypeEnums.valueOf(tHsDealRulesInfo.getType()));
        }
        // 执行处理器
        HolmesDeal alertDeal = DealContext.getDeal(dealName);
        alertDeal.deal(object);
    }


}
