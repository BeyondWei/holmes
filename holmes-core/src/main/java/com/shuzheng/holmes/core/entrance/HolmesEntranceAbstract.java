package com.shuzheng.holmes.core.entrance;

import java.util.Timer;
import java.util.TimerTask;

public abstract class HolmesEntranceAbstract implements HolmesEntrance {

    protected long delay = 0;
    protected long intevalPeriod = 1000;
    protected boolean isOpenTimer = false;


    public void start(Object... o) {

        receive(o);

        postProcessing();

        if (isOpenTimer) {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    supplement();
                }
            };
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, delay, intevalPeriod);
        }
    }

}
