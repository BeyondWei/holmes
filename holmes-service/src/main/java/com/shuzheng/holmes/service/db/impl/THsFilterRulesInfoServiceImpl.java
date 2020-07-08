package com.shuzheng.holmes.service.db.impl;

import com.shuzheng.holmes.common.entity.THsFilterRulesInfo;
import com.shuzheng.holmes.dao.mapper.THsFilterRulesInfoMapper;
import com.shuzheng.holmes.service.db.THsFilterRulesInfoService;
import com.shuzheng.holmes.service.plugin.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class THsFilterRulesInfoServiceImpl extends BaseServiceImpl<THsFilterRulesInfo> implements THsFilterRulesInfoService {

    @Resource
    private THsFilterRulesInfoMapper tHsFilterRulesInfoMapper;

    @Override
    public List<THsFilterRulesInfo> getTHsFilterRulesInfoByFilterGroupUuid(String filterGroupUuid) {
        return tHsFilterRulesInfoMapper.getTHsFilterRulesInfoByFilterGroupUuid(filterGroupUuid);
    }
}
