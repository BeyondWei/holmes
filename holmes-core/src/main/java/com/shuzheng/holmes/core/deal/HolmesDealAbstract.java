package com.shuzheng.holmes.core.deal;

import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.context.DealContext;
import com.shuzheng.holmes.core.enums.DealTypeEnums;
import com.shuzheng.holmes.core.deal.HolmesDeal;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 处理器抽象类
 */
public abstract class HolmesDealAbstract implements HolmesDeal {

    protected String dealName;
    protected DealTypeEnums dealTypeEnums;
    protected ConfigContext configContext;
    protected AtomicLong atomicLong = new AtomicLong(0);

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

    public AtomicLong getAtomicLong() {
        return atomicLong;
    }

    protected abstract void deal(Object msg);

    @Override
    public void run(Object msg){
        atomicLong.incrementAndGet();
        deal(msg);
    }

    public void register() {
        if (!DealContext.isExist(getDealName())) {
            DealContext.register(this);
        }
    }


}
