package com.shuzheng.holmes.dao.mapper;

import com.shuzheng.holmes.common.entity.THsDealRulesInfo;
import com.shuzheng.holmes.dao.plugin.TkMybatisMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface THsDealRulesInfoMapper extends TkMybatisMapper<THsDealRulesInfo> {

    List<THsDealRulesInfo> getTHsDealRulesInfoByDealGroupUuid(@Param("dealGroupUuid") String dealGroupUuid);
}