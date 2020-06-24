package com.shuzheng.holmes.server;

import com.shuzheng.holmes.common.dto.FlumeData;
import com.shuzheng.holmes.common.utils.FlumeAvroUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class KafkaCustomer {

    @KafkaListener(topics = "netcat")
    public void receive(String record) {
        System.out.println("topic========topic");
        FlumeData flumeData = FlumeAvroUtils.getFlumeData(record);
        if(flumeData.getHeaderMap().get("type").equals("szfh")){
            System.out.println(flumeData.getMsg());
        }
    }

}