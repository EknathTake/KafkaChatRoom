package com.example.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * @author eknathtake
 */
@Configuration
public class KafkaTopicConfig {

	@Value("${spring.kafka.topic}")
	private String topicName;
	
	@Bean
	public NewTopic privateTopic() {
		return TopicBuilder.name(topicName)
				.partitions(10)
				.replicas(0)
				.compact().build();
	}
}
