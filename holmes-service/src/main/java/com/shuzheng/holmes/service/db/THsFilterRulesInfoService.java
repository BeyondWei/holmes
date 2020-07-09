package com.shuzheng.holmes.service.db;

import com.shuzheng.holmes.common.entity.THsFilterRulesInfo;
import com.shuzheng.holmes.service.plugin.BaseService;

import java.util.List;

public interface THsFilterRulesInfoService extends BaseService<THsFilterRulesInfo> {

    /**
     * 获取一组过滤信息
     */
    List<THsFilterRulesInfo> getTHsFilterRulesInfoByFilterGroupUuidAndLogUuid(String filterGroupUuid,String logUuid);
}
