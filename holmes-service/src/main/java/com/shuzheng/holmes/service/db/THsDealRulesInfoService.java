package com.shuzheng.holmes.service.db;

import com.shuzheng.holmes.common.entity.THsDealRulesInfo;
import com.shuzheng.holmes.service.plugin.BaseService;

import java.util.List;

public interface THsDealRulesInfoService extends BaseService<THsDealRulesInfo> {

    /**
     * 获取处理组
     * @param dealGroupUuid
     * @return
     */
    List<THsDealRulesInfo> getTHsDealRulesInfoByDealGroupUuid(String dealGroupUuid);
}
