package com.shuzheng.holmes.core.deal;

import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.context.DealContext;
import com.shuzheng.holmes.core.enums.DealTypeEnums;
import com.shuzheng.holmes.core.deal.HolmesDeal;

/**
 * 处理器抽象类
 */
public abstract class HolmesDealAbstract implements HolmesDeal {

    protected String dealName;
    protected DealTypeEnums dealTypeEnums;
    protected ConfigContext configContext;

    @Override
    public DealTypeEnums getDealType() {
        return dealTypeEnums;
    }

    @Override
    public String getDealName() {
        return dealName;
    }

    public ConfigContext getConfigContext() {
        return configContext;
    }

    @Override
    public abstract void deal(Object msg);

    public void register() {
        if (!DealContext.isExist(getDealName())) {
            DealContext.register(this);
        }
    }


}
