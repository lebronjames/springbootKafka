package com.aspire.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerService {

	@KafkaListener(topics= {"my-replicated-topic1","my-replicated-topic2"})
	public void processMessage(String content) {
		System.out.println("-------------:"+content);
	}
}
