package com.shuzheng.holmes.server.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class KafkaProducerTask {

	private static Logger logger = LoggerFactory.getLogger(KafkaProducerTask.class);

	@Autowired
	private KafkaTemplate kafkaTemplate;

	//发送消息方法
	@RequestMapping("send")
    public void send() {
		kafkaTemplate.send("netcat", "111111");
    }


}

