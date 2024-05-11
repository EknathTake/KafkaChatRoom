package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Message;
import com.example.demo.service.KafkaService;


/**
 * @author eknathtake
 */
@RestController
public class MessageController {

	Logger logger = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private KafkaService service;

	@PostMapping(value = "/sendMessage"/* , consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } */)
	public void sendMessage(@RequestParam(name = "senderNumber") String senderNumber,
			@RequestParam(name = "kafkaKey") String kafkaKey,
			@RequestParam(name = "recepientNumber") String recepientNumber,
			@RequestParam(name = "message") String message, @RequestParam(name = "file") MultipartFile file)
			throws IOException {
		
		Double uuid = Math.random();
		Message msg = new Message();
		msg.setUuid(uuid);
		msg.setMessageBody(message);
		msg.setSenderNumber(senderNumber);
		msg.setRecepientNumber(recepientNumber);
		msg.setKey(kafkaKey);

		service.writeFile(uuid, file);
		logger.info("Sending Message: {}", message);
		service.send(kafkaKey, msg);
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
