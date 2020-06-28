package com.shuzheng.holmes.core.filter;

import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.context.FilterContext;
import com.shuzheng.holmes.core.enums.FilterTypeEnums;

/**
 * 过滤器抽象类
 */
public abstract class HolmesFilterAbstract implements HolmesFilter {

    private String filterName = this.getClass().getName();
    protected FilterTypeEnums filterTypeEnums;
    protected ConfigContext configContext;

    @Override
    public FilterTypeEnums getFilterType() {
        return filterTypeEnums;
    }

    @Override
    public String getFilterName() {
        return filterName;
    }

    public ConfigContext getConfigContext() {
        return configContext;
    }

    @Override
    public abstract Object filter(Object msg);

    public void register() {
        if (!FilterContext.isExist(getFilterName())) {
            FilterContext.register(this);
        }
    }


}
