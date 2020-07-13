package com.shuzheng.holmes.core.filter;

import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.context.FilterContext;
import com.shuzheng.holmes.core.enums.FilterTypeEnums;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 过滤器抽象类
 */
public abstract class HolmesFilterAbstract implements HolmesFilter {

    protected String filterName;
    protected FilterTypeEnums filterTypeEnums;
    protected ConfigContext configContext;
    protected AtomicLong atomicLong = new AtomicLong(0);
    protected String results;

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

    public AtomicLong getAtomicLong() {
        return atomicLong;
    }

    public String getResults() {
        return results;
    }

    /**
     * 过滤器实现
     */
    protected abstract Object filter(Object msg);

    @Override
    public Object run(Object msg) {
        results = msg.toString();
        atomicLong.incrementAndGet();
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
