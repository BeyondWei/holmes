package com.shuzheng.holmes.core.entrance;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class HolmesEntranceAbstract implements HolmesEntrance, ApplicationListener<ContextRefreshedEvent> {

    protected static AtomicInteger COUNT = new AtomicInteger(0);

    protected long delay = 0;
    protected long intevalPeriod = 1000;


    public void start(Object... o) {

        receive(o);

        postProcessing();

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new Thread(()->init()).start();
    }
}
