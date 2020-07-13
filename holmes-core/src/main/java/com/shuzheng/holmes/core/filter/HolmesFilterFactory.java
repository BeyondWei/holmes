package com.shuzheng.holmes.core.filter;

import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.context.FilterContext;
import com.shuzheng.holmes.core.enums.FilterTypeEnums;
import lombok.NonNull;

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
                                                    @NonNull String filterName,
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
     * @param holmesFilterAbstractClass
     * @param holmesFilterName
     * @param configContext
     * @param filterTypeEnums
     */
    public static void createAndRegister(Class<? extends HolmesFilterAbstract> holmesFilterAbstractClass,
                                         @NonNull String holmesFilterName,
                                         ConfigContext configContext,
                                         FilterTypeEnums filterTypeEnums) {
        createFilter(holmesFilterAbstractClass, holmesFilterName, configContext, filterTypeEnums).register();
    }

    /**
     * 更新过滤器规则
     * @param holmesFilterName
     * @param configContext
     */
    public static void updateFilter(@NonNull String holmesFilterName, ConfigContext configContext) {
        HolmesFilterAbstract filter = (HolmesFilterAbstract) FilterContext.getFilter(holmesFilterName);
        filter.configContext = configContext;
    }


}
