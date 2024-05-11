package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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

	@PostMapping(value = "/sendMessage"/* , consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE} */)
	public void sendMessage(
			@RequestParam(name = "senderNumber", value = "senderNumber", required = false) String senderNumber,
			@RequestParam(name = "kafkaKey", value = "kafkaKey", required = false) String kafkaKey,
			@RequestParam(name = "recepientNumber", value = "recepientNumber", required = false) String recepientNumber,
			@RequestParam(name = "message", value = "message", required = false) String message,
			@RequestPart(name = "file", value = "file", required = false) MultipartFile file)
			throws IOException {
		
		Message msg = mapMessageRequest(senderNumber, kafkaKey, recepientNumber, message, Math.random());

		service.writeFile(msg.getUuid(), file);
		logger.info("Sending Message: {}", message);
		service.send(kafkaKey, msg);
		logger.info("Message Sent");
	}

	private Message mapMessageRequest(String senderNumber, String kafkaKey, String recepientNumber, String message,
			Double uuid) {
		Message msg = new Message();
		msg.setUuid(uuid);
		msg.setMessageBody(message);
		msg.setSenderNumber(senderNumber);
		msg.setRecepientNumber(recepientNumber);
		msg.setKey(kafkaKey);
		return msg;
	}

	@GetMapping("/readMessage")
	public List<String> readMessage() {
		logger.info("Reading Message");
		List<String> records = service.getAllMessages();
		logger.info("Reading Message: {}", records);
		return records;
	}
}
