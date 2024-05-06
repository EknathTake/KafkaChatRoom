	package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.model.Message;

@Service
public class KafkaService {
	
	@Autowired
	private KafkaTemplate<String, Message> kafkaTemplate;

	@Value("${spring.kafka.topic}")
	private String kafkaTopic;
	
	private List<Message> list = new ArrayList<>();
	
	Logger logger = LoggerFactory.getLogger(KafkaService.class);

	public void send(String key, Message message) {
		kafkaTemplate.send(kafkaTopic,key, message);
	}
	
	@KafkaListener(topics = "${spring.kafka.topic}", containerFactory = "mobileContinerFactory")
	public void listenMessages(Message message) {
		logger.info("Received Message: {}",message);
		list.add(message);
	}
	
	public List<String> getAllMessages(){
		return list.stream()
				.map(Message::getMessageBody)
				.map(message -> message + "<br/>")
				.collect(Collectors.toList());
	}
}
