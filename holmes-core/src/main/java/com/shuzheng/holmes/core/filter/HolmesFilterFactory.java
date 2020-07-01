package com.shuzheng.holmes.core.filter;

import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.enums.FilterTypeEnums;
import com.sun.istack.internal.NotNull;

public class HolmesFilterFactory {

    private HolmesFilterFactory() {
    }

    /**
     * @param holmesFilterAbstractClass
     * @param configContext
     * @param filterTypeEnums
     * @return
     */
    public static HolmesFilterAbstract createFilter(Class<? extends HolmesFilterAbstract> holmesFilterAbstractClass,
                                               @NotNull String filterName,
                                               ConfigContext configContext,
                                               FilterTypeEnums filterTypeEnums) {
        HolmesFilterAbstract holmesFilter = null;
        try {
            holmesFilter = holmesFilterAbstractClass.newInstance();
            holmesFilter.filterName = filterName;
            holmesFilter.configContext = configContext;
            holmesFilter.filterTypeEnums = filterTypeEnums;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return holmesFilter;
    }

    /**
     *
     * @param holmesFilterAbstractClass
     * @param holmesFilterName
     * @param configContext
     * @param filterTypeEnums
     */
    public static void createAndRegister(Class<? extends HolmesFilterAbstract> holmesFilterAbstractClass,
                                                          @NotNull String holmesFilterName,
                                                          ConfigContext configContext,
                                                          FilterTypeEnums filterTypeEnums) {
        createFilter(holmesFilterAbstractClass,holmesFilterName,configContext,filterTypeEnums).register();
    }

}
