package com.shuzheng.holmes.core.filter;

import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.context.FilterContext;
import com.shuzheng.holmes.core.enums.FilterTypeEnums;

/**
 * 过滤器抽象类
 */
public abstract class HolmesFilterAbstract implements HolmesFilter {

    private String filterName = this.getClass().getName();
    public FilterTypeEnums filterTypeEnums;
    public ConfigContext configContext;

    @Override
    public FilterTypeEnums getFilterType() {
        return filterTypeEnums;
    }

    @Override
    public String getFilterName() {
        return filterName;
    }

    @Override
    public abstract Object filter(Object msg);

    public void register() {
        if (!FilterContext.isExist(getFilterName())) {
            FilterContext.register(this);
        }
    }


}
