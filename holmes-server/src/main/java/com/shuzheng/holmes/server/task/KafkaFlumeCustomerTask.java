package com.shuzheng.holmes.server.task;

import com.shuzheng.holmes.common.Constants;
import com.shuzheng.holmes.common.dto.DataFormat;
import com.shuzheng.holmes.common.utils.FlumeAvroUtils;
import com.shuzheng.holmes.common.utils.StringUtils;
import com.shuzheng.holmes.common.utils.ThreadPoolFactory;
import com.shuzheng.holmes.service.bussiness.BusHolmesServerService;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class KafkaFlumeCustomerTask implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private BusHolmesServerService busHolmesServerService;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    private KafkaProperties kafkaProperties;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected static AtomicInteger COUNT = new AtomicInteger(0);
    protected static AtomicInteger atomicInteger = new AtomicInteger(0);

    @KafkaListener(topics = "netcat", id = "kafkaListener")
    public void run(ConsumerRecord<Integer, String> record) {

//        System.out.println(record.offset() + " ========= " + df.format(new Date()));
        // todo 如何防止数据丢失
//        tHsKafkaLogInfoService.insertNotNull(tHsKafkaLogInfo);
//        DataOffset.creatFile("kafka", record.topic() + "-" + record.partition() + "-" + record.offset(), record.value());
        DataFormat flumeData = FlumeAvroUtils.getFlumeData(record.value());
        String logUuid = flumeData.getHeaderMap().get(Constants.HOLMES_LOG_UUID);
        String projectUuid = flumeData.getHeaderMap().get(Constants.HOLMES_PROJECT_UUID);
        if (!StringUtils.isEmpty(logUuid) && !StringUtils.isEmpty(projectUuid) && busHolmesServerService.isExist(projectUuid, logUuid)) {
            COUNT.incrementAndGet();
//            THsKafkaLogInfo tHsKafkaLogInfo = new THsKafkaLogInfo();
//            tHsKafkaLogInfo.setCreatetime(new Date());
//            tHsKafkaLogInfo.setKafkaUrl(kafkaProperties.getBootstrapServers().toString());
//            tHsKafkaLogInfo.setTopic(record.topic());
//            tHsKafkaLogInfo.setPartition(record.partition());
//            tHsKafkaLogInfo.setOffset(record.offset());
//            flumeData.setId(tHsKafkaLogInfo.getId());
            ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.getInstance().autoCreate(projectUuid + "-" + logUuid, 10000);
            FilterTask filterTask = new FilterTask(flumeData,COUNT);
            threadPoolExecutor.execute(filterTask);
        }
    }



    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (COUNT.get() < 1000) {
                    if (kafkaListenerEndpointRegistry.getListenerContainer("kafkaListener").isPauseRequested()) {
                        kafkaListenerEndpointRegistry.getListenerContainer("kafkaListener").resume();
                        System.out.println("继续");
                    }
                } else {
                    if (!kafkaListenerEndpointRegistry.getListenerContainer("kafkaListener").isPauseRequested()) {
                        kafkaListenerEndpointRegistry.getListenerContainer("kafkaListener").pause();
                        System.out.println("暂停");
                    }
                }
            }
        }).start();

    }
}