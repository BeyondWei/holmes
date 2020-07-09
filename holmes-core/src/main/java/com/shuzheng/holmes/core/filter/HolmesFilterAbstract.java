package com.shuzheng.holmes.core.filter;

import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.context.FilterContext;
import com.shuzheng.holmes.core.enums.FilterTypeEnums;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * 过滤器抽象类
 */
public abstract class HolmesFilterAbstract implements HolmesFilter {

    protected String filterName;
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

    /**
     * 过滤器实现
     */
    public abstract Object filter(Object msg);

    @Override
    public Object run(Object msg) {
        String hashMapKey = configContext.getString("hashMapKey");
        if (!StringUtils.isEmpty(hashMapKey)) {
            msg = ((HashMap) msg).get(hashMapKey);
        }
        return filter(msg);
    }

    public void register() {
        if (!FilterContext.isExist(getFilterName())) {
            FilterContext.register(this);
        }
    }


}
