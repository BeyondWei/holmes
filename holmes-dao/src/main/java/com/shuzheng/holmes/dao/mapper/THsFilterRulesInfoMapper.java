package com.shuzheng.holmes.dao.mapper;

import com.shuzheng.holmes.common.entity.THsFilterRulesInfo;
import com.shuzheng.holmes.dao.plugin.TkMybatisMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface THsFilterRulesInfoMapper extends TkMybatisMapper<THsFilterRulesInfo> {

    List<THsFilterRulesInfo> getTHsFilterRulesInfoByFilterGroupUuidAndLogUuid(@Param("filterGroupUuid") String filterGroupUuid,@Param("logUuid") String logUuid);

}