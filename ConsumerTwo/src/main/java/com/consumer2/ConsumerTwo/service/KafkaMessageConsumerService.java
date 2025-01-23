package com.consumer2.ConsumerTwo.service;

import com.consumer2.ConsumerTwo.dto.KafkaMessage;
import com.consumer2.ConsumerTwo.model.MessageModel;
import com.consumer2.ConsumerTwo.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class KafkaMessageConsumerService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ObjectMapper objectMapper;

    String dummyApiUrl = "http://localhost:1111/dummyapi";

    @KafkaListener(topics = "first")
    public void consumeMessage(String kafkaMessageJson) {
        try {
            // Converting JSON into Object of KafkaMessage DTO
            KafkaMessage messagePayload = objectMapper.readValue(kafkaMessageJson, KafkaMessage.class);

            // Extract UUID and message content
            String messageId = messagePayload.getUuid();
            String receivedMessage = messagePayload.getMessage();
            System.out.println("receivedMessage "+receivedMessage);

            // Save message data to the database
            MessageModel messageModel = new MessageModel(messageId, receivedMessage, null);
            messageRepository.save(messageModel);

            // Send message to external API and get response
            RestTemplate apiRestTemplate = new RestTemplate();
            String response = apiRestTemplate.postForObject(dummyApiUrl, receivedMessage, String.class);

            // Update the database with the API response
            Optional<MessageModel> messageModelOptional = messageRepository.findById(messageId);
            if(messageModelOptional.isPresent()) {
                MessageModel messageModelToUpdate = messageModelOptional.get();
                messageModelToUpdate.setResponse(response);
                messageRepository.save(messageModelToUpdate);
//                System.out.println("added message "+messageModelToUpdate.getResponse());
            }

        } catch (Exception e) {
            System.err.println("Error processing Kafka message: " + kafkaMessageJson);
            e.printStackTrace();
        }
    }
}
