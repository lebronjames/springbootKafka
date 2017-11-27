package com.aspire.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aspire.demo.service.ConsumerService;
import com.aspire.demo.service.ProducerService;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

	@Autowired
	ProducerService producerService;
	@Autowired
	ConsumerService consumerService;
	
	@GetMapping("/test")
	public void test() {
		producerService.send();
	}
}
