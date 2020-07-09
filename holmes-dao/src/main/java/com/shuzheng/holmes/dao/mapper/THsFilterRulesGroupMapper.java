package com.shuzheng.holmes.dao.mapper;

import com.shuzheng.holmes.common.entity.THsFilterRulesGroup;
import com.shuzheng.holmes.dao.plugin.TkMybatisMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface THsFilterRulesGroupMapper extends TkMybatisMapper<THsFilterRulesGroup> {

    List<THsFilterRulesGroup> getFilterRulesGroupByProjectUuid(@Param("projectUuid") String projectUuid);
}