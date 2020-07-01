package com.shuzheng.holmes.server.task;

import com.shuzheng.holmes.common.dto.FlumeData;
import com.shuzheng.holmes.common.utils.FlumeAvroUtils;
import com.shuzheng.holmes.common.utils.ThreadPoolFactory;
import com.shuzheng.holmes.server.task.FilterTask;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Component
public class KafkaCustomerTask {

    private static final String TPYE = "type";

    private ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(10, 20
                    , 30, TimeUnit.SECONDS, new LinkedBlockingQueue());


    @KafkaListener(topics = "netcat")
    public void receive(ConsumerRecord<Integer, String> record) {
        System.out.println("---------------" + record.partition() + "  " + record.offset());

        Thread thread = new Thread(() -> {
            FlumeData flumeData = FlumeAvroUtils.getFlumeData(record.value());
            ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.getInstance().autoCreate(flumeData.getHeaderMap().get(TPYE));
            FilterTask filterTask = new FilterTask(flumeData);
            threadPoolExecutor.execute(filterTask);
        });
        threadPoolExecutor.execute(thread);

    }


}