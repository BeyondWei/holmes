package com.shuzheng.holmes.service.db.impl;

import com.shuzheng.holmes.common.entity.THsFilterRulesGroup;
import com.shuzheng.holmes.dao.mapper.THsFilterRulesGroupMapper;
import com.shuzheng.holmes.service.db.THsFilterRulesGroupService;
import com.shuzheng.holmes.service.plugin.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class THsFilterRulesGroupServiceImpl extends BaseServiceImpl<THsFilterRulesGroup> implements THsFilterRulesGroupService {

    @Resource
    private THsFilterRulesGroupMapper tHsFilterRulesGroupMapper;

    @Override
    public List<THsFilterRulesGroup> getFilterRulesGroupByProjectUuid(String projectUuid) {
        return tHsFilterRulesGroupMapper.getFilterRulesGroupByProjectUuid(projectUuid);
    }
}
