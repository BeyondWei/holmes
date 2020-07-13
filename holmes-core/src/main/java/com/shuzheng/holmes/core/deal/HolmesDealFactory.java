package com.shuzheng.holmes.core.deal;

import com.shuzheng.holmes.core.context.ConfigContext;
import com.shuzheng.holmes.core.context.DealContext;
import com.shuzheng.holmes.core.enums.DealTypeEnums;
import lombok.NonNull;

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
                                                @NonNull String dealName,
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
     * @param holmesDealAbstractClass
     * @param dealName
     * @param configContext
     * @param dealTypeEnums
     */
    public static void createAndRegister(Class<? extends HolmesDealAbstract> holmesDealAbstractClass,
                                         @NonNull String dealName,
                                         ConfigContext configContext,
                                         DealTypeEnums dealTypeEnums) {
        createDeal(holmesDealAbstractClass, dealName, configContext, dealTypeEnums).register();
    }

    /**
     * 更新处理器规则
     * @param dealName
     * @param configContext
     */
    public static void updateDeal(@NonNull String dealName, ConfigContext configContext) {
        HolmesDealAbstract dealAbstract = (HolmesDealAbstract) DealContext.getDeal(dealName);
        dealAbstract.configContext = configContext;
    }

}
