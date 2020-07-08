package com.shuzheng.holmes.service.bussiness.impl;

import com.shuzheng.holmes.common.Constants;
import com.shuzheng.holmes.common.ConstantsEnum;
import com.shuzheng.holmes.common.entity.*;
import com.shuzheng.holmes.service.bussiness.BusHolmesServerService;
import com.shuzheng.holmes.service.db.*;
import com.shuzheng.holmes.service.plugin.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusHomlesServerServiceImpl implements BusHolmesServerService {

    @Autowired
    private THsKafkaLogInfoService tHsKafkaLogInfoService;

    @Autowired
    private THsFilterRulesGroupService tHsFilterRulesGroupService;

    @Autowired
    private THsFilterRulesInfoService tHsFilterRulesInfoService;

    @Autowired
    private THsDealRulesGroupService tHsDealRulesGroupService;

    @Autowired
    private THsDealRulesInfoService tHsDealRulesInfoService;

    @Autowired
    private THsExFlumeSourceInfoService tHsExFlumeSourceInfoService;


    @Override
    public List<THsFilterRulesGroup> getFilterRulesGroupByLogUuid(String logUuid) {
        THsFilterRulesGroup tHsFilterRulesGroup = new THsFilterRulesGroup();
        tHsFilterRulesGroup.setIsEffective(ConstantsEnum.DATA_STATUS.DELETE.getStatus());
        tHsFilterRulesGroup.setLogUuid(logUuid);
        return tHsFilterRulesGroupService.select(tHsFilterRulesGroup);
    }

    @Override
    public List<THsFilterRulesInfo> getTHsFilterRulesInfoByFilterGroupUuid(String logUuid) {
        return tHsFilterRulesInfoService.getTHsFilterRulesInfoByFilterGroupUuid(logUuid);
    }

    @Override
    public boolean isExist(String projectUuid, String logUuid) {
        THsExFlumeSourceInfo tHsExFlumeSourceInfo = new THsExFlumeSourceInfo();
        tHsExFlumeSourceInfo.setLogUuid(logUuid);
        tHsExFlumeSourceInfo.setProjectUuid(projectUuid);
        return tHsExFlumeSourceInfoService.select(tHsExFlumeSourceInfo) != null;
    }

    @Override
    public List<THsDealRulesInfo> getTHsDealRulesInfoByDealGroupUuid(String dealGroupUuid) {
        return tHsDealRulesInfoService.getTHsDealRulesInfoByDealGroupUuid(dealGroupUuid);
    }
}
