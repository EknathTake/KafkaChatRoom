package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Message;
import com.example.demo.service.KafkaService;

@RestController
public class MessageController {

	Logger logger = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private KafkaService service;

	@PostMapping(value = "/sendMessage", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public void sendMessage(@RequestParam(name = "key") String key,
			@RequestParam(name = "senderNumber") String senderNumber,
			@RequestParam(name = "recepientNumber") String recepientNumber, @RequestParam(name = "msg") String msg,
			@RequestParam(name = "file") MultipartFile file) {
		Message message = new Message();
		message.setMessageBody(msg);
		message.setSenderNumber(senderNumber);
		message.setRecepientNumber(recepientNumber);
		logger.info("Sending Message: {}", message);
		service.send(key, message);
		logger.info("Message Sent");
	}

	@GetMapping("/readMessage")
	public List<String> readMessage() {
		logger.info("Reading Message");
		List<String> records = service.getAllMessages();
		logger.info("Reading Message: {}", records);
		return records;
	}
}
