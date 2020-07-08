package com.shuzheng.holmes.service.db.impl;

import com.shuzheng.holmes.common.entity.THsDealRulesInfo;
import com.shuzheng.holmes.dao.mapper.THsDealRulesInfoMapper;
import com.shuzheng.holmes.service.db.THsDealRulesInfoService;
import com.shuzheng.holmes.service.plugin.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class THsDealRulesInfoServiceImpl extends BaseServiceImpl<THsDealRulesInfo> implements THsDealRulesInfoService {


    @Resource
    private THsDealRulesInfoMapper tHsDealRulesInfoMapper;


    @Override
    public List<THsDealRulesInfo> getTHsDealRulesInfoByDealGroupUuid(String dealGroupUuid) {
        return tHsDealRulesInfoMapper.getTHsDealRulesInfoByDealGroupUuid(dealGroupUuid);
    }
}
