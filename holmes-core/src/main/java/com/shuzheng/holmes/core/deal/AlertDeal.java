package com.shuzheng.holmes.core.deal;

public class AlertDeal extends HolmesDealAbstract {

    @Override
    public void deal(Object msg) {
        System.out.println("我要告警");
    }

}
