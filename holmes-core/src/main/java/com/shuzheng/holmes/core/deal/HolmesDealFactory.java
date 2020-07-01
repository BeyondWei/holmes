package com.shuzheng.holmes.core.deal;

import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.enums.DealTypeEnums;
import com.sun.istack.internal.NotNull;

public class HolmesDealFactory {

    private HolmesDealFactory() {
    }

    /**
     * @param holmesDealAbstractClass
     * @param configContext
     * @param dealTypeEnums
     * @return
     */
    public static HolmesDealAbstract createDeal(Class<? extends HolmesDealAbstract> holmesDealAbstractClass,
                                                    @NotNull String dealName,
                                                    ConfigContext configContext,
                                                    DealTypeEnums dealTypeEnums) {
        HolmesDealAbstract holmesDeal = null;
        try {
            holmesDeal = holmesDealAbstractClass.newInstance();
            holmesDeal.dealName = dealName;
            holmesDeal.configContext = configContext;
            holmesDeal.dealTypeEnums = dealTypeEnums;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return holmesDeal;
    }

    /**
     *
     * @param holmesDealAbstractClass
     * @param dealName
     * @param configContext
     * @param dealTypeEnums
     */
    public static void createAndRegister(Class<? extends HolmesDealAbstract> holmesDealAbstractClass,
                                                          @NotNull String dealName,
                                                          ConfigContext configContext,
                                                          DealTypeEnums dealTypeEnums) {
        createDeal(holmesDealAbstractClass,dealName,configContext,dealTypeEnums).register();
    }

}
