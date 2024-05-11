	package com.example.demo.service;

import java.io.FileOutputStream;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Message;

/**
 * @author eknathtake
 */
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

	/**
	 * Writing file into kafka topic has been considered bad idea due to the size of the file. Although we can 
	 * Store file in chunk but there is a high possibility of data is getting corrupted due to missing chunk.
	 * As per my understanding either we can store file in DB or in writing it in server could be the best approach.
	 * There will be size issue but we can write scheduler to clean files after specific time, to resolve this issue.
	 * @param uuid
	 * @param file
	 * @throws IOException
	 */
	public void writeFile(Double uuid, MultipartFile file) throws IOException {
		logger.info("Writing file to location src/main/resources/files");
		FileOutputStream outputStream = new FileOutputStream(
				"src/main/resources/files/" + String.valueOf(uuid) + "_" + file.getOriginalFilename());
		byte[] strToBytes = file.getBytes();
		outputStream.write(strToBytes);
		outputStream.close();
		logger.info("Writing file to location src/main/resources/files completed");
		
	}
}
