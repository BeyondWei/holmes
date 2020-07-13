package com.shuzheng.holmes.server.task;

import com.shuzheng.holmes.common.Constants;
import com.shuzheng.holmes.common.dto.DataFormat;
import com.shuzheng.holmes.common.utils.StringUtils;
import com.shuzheng.holmes.common.utils.ThreadPoolFactory;
import com.shuzheng.holmes.core.entrance.HolmesEntranceAbstract;
import com.shuzheng.holmes.server.dto.HolmesHttpDto;
import com.shuzheng.holmes.service.bussiness.BusHolmesServerService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/httpEntranceTask")
public class HttpEntranceTask extends HolmesEntranceAbstract {

    @Autowired
    private BusHolmesServerService busHolmesServerService;

    private static boolean flag = true;

    private static final Logger log = LoggerFactory.getLogger(HttpEntranceTask.class);


    protected static AtomicInteger atomicInteger = new AtomicInteger(0);

    @SneakyThrows
    @Override
    public void init() {
        while (true) {
            Thread.sleep(50);
            if (COUNT.get() < 100000) {
                if (!flag) {
                    flag = true;
                }
            } else {
                if (flag) {
                    flag = false;
                }
            }
        }
    }

    @Override
    public void receive(Object... object) {
        DataFormat dataFormat = (DataFormat) object[0];
        String logUuid = dataFormat.getHeaderMap().get(Constants.HOLMES_LOG_UUID);
        String projectUuid = dataFormat.getHeaderMap().get(Constants.HOLMES_PROJECT_UUID);
        if (!StringUtils.isEmpty(logUuid) && !StringUtils.isEmpty(projectUuid) && busHolmesServerService.isExist(projectUuid, logUuid)) {
            COUNT.incrementAndGet();
            ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.getInstance().autoCreate(projectUuid + "-" + logUuid, 10000);
            FilterTask filterTask = new FilterTask(dataFormat, COUNT);
            threadPoolExecutor.execute(filterTask);
        }
    }


    @Override
    public void postProcessing() {

    }


    @PostMapping("/receive")
    public Mono<String> receive(@RequestHeader("projectUuid") String projectUuid, @RequestHeader("logUuid") String logUuid,
                                @RequestBody HolmesHttpDto data) {

        String msg = data.getMsg();
        if (!flag) {
            log.error("-------------程序压力过大，停止接收--------------");
            return Mono.just("程序压力过大，停止接收");
        }
        if (StringUtils.isEmpty(projectUuid)) {
            data.setProjectUuid(projectUuid);
        }
        if (StringUtils.isEmpty(logUuid)) {
            data.setLogUuid(logUuid);
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constants.HOLMES_PROJECT_UUID, projectUuid);
        hashMap.put(Constants.HOLMES_LOG_UUID, logUuid);
        start(new DataFormat(hashMap, msg));
        return Mono.just(msg);
    }

    @RequestMapping("getTotal")
    public Integer getTotal() {
        return atomicInteger.get();
    }

    @RequestMapping("reset")
    public Integer reset() {
        atomicInteger = new AtomicInteger(0);
        return atomicInteger.get();
    }
}
