package com.shuzheng.holmes.sdk.http;

import com.shuzheng.holmes.sdk.flume.common.StringUtils;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class HolmesHttpLog4jAppender extends AppenderSkeleton {


    private ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(10, 20
                    , 30, TimeUnit.SECONDS, new LinkedBlockingQueue(1000),
                    new RejectedExecutionHandler() {
                        @Override
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                            new RuntimeException("信息发送线程池已满！");
                        }
                    });

    private String hosts;
    private String url;
    private HashMap<String, String> headMap = new HashMap<>();
    private String projectUuid;
    private String logUuid;
    private Integer interval = 100;
    private Integer capacity = Integer.MAX_VALUE;
    private ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    public void setCapacity(String capacity) {
        this.capacity = Integer.parseInt(capacity);
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    public void setLogUuid(String logUuid) {
        this.logUuid = logUuid;
    }


    @Override
    public void activateOptions() {
        this.url = "http://" + hosts + "/httpEntranceTask/receive";
        this.headMap.put("projectUuid", projectUuid);
        this.headMap.put("logUuid", logUuid);
        new Thread(() ->
                send()
        ).start();
    }

    public void send() {
        Integer actualInterval;
        while (true) {
            if (concurrentLinkedQueue.size() >= capacity) {
                actualInterval = 0;
            } else {
                actualInterval = interval;
            }
            try {
                Thread.sleep(actualInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!concurrentLinkedQueue.isEmpty()) {
                threadPoolExecutor.execute(() -> {
                    Object poll = concurrentLinkedQueue.poll();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("msg", poll.toString());
                    HttpClientUtil.sendPostJson(this.url, map, this.headMap);
                });
            }
        }
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        if (!loggingEvent.getLoggerName().contains("com.shuzheng.holmes.sdk")) {
            concurrentLinkedQueue.add(loggingEvent.getMessage());
        }
    }

    @Override
    public void close() {
        HttpClientUtil.idleConnection();
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

}
