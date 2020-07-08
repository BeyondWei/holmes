package com.shuzheng.holmes.service.bussiness;

import com.shuzheng.holmes.common.entity.THsDealRulesInfo;
import com.shuzheng.holmes.common.entity.THsFilterRulesGroup;
import com.shuzheng.holmes.common.entity.THsFilterRulesInfo;
import com.shuzheng.holmes.common.entity.THsKafkaLogInfo;
import com.shuzheng.holmes.service.plugin.BaseService;

import java.util.List;

public interface BusHolmesServerService {

    /**
     * 获取日志规则组
     */
    List<THsFilterRulesGroup> getFilterRulesGroupByLogUuid(String logUuid);

    /**
     * 获取日志规则组详细信息
     */
    List<THsFilterRulesInfo> getTHsFilterRulesInfoByFilterGroupUuid(String logUuid);

    /**
     * 判断项目和日志规则是否存在
     */
    boolean isExist(String projectUuid, String logUuid);

    /**
     * 获取处理组
     */
    List<THsDealRulesInfo> getTHsDealRulesInfoByDealGroupUuid(String dealGroupUuid);
}
