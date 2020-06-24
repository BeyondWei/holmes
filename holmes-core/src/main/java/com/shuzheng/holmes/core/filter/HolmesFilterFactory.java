package com.shuzheng.holmes.core.filter;

import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.enums.FilterTypeEnums;

public class HolmesFilterFactory {

    private HolmesFilterFactory() {
    }

    /**
     *
     * @param holmesFilterAbstractClass
     * @param configContext
     * @param filterTypeEnums
     * @return
     */
    public static HolmesFilterAbstract factory(Class<? extends HolmesFilterAbstract> holmesFilterAbstractClass,
                                       ConfigContext configContext,
                                       FilterTypeEnums filterTypeEnums) {
        HolmesFilterAbstract holmesFilter = null;
        try {
            holmesFilter = holmesFilterAbstractClass.newInstance();
            holmesFilter.configContext = configContext;
            holmesFilter.filterTypeEnums = filterTypeEnums;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return holmesFilter;
    }
}
