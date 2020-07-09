package com.shuzheng.holmes.service.db;

import com.shuzheng.holmes.common.entity.THsFilterRulesGroup;
import com.shuzheng.holmes.service.plugin.BaseService;

import java.util.List;

public interface THsFilterRulesGroupService extends BaseService<THsFilterRulesGroup> {

    /**
     * 获取过滤组
     * @param projectUuid
     * @return
     */
    List<THsFilterRulesGroup> getFilterRulesGroupByProjectUuid(String projectUuid);
}
