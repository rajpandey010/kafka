package com.consumer.Consumer_Project.service;

import com.consumer.Consumer_Project.modal.MessageModel;
import com.consumer.Consumer_Project.repository.MessageRepository;
import com.consumer.Consumer_Project.dto.KafkaMessage; // Renamed DTO for clarity
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

    String dummyApiUrl = "https://nx51np36-2707.inc1.devtunnels.ms/secured/apigateway/bulkapi";

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
//            MessageModel messageModel = new MessageModel(messageId, receivedMessage, null);
//            messageRepository.save(messageModel);

            // Send message to external API and get response
            RestTemplate apiRestTemplate = new RestTemplate();
            String response = apiRestTemplate.postForObject(dummyApiUrl, receivedMessage, String.class);

            // Update the database with the API response
//            Optional<MessageModel> messageModelOptional = messageRepository.findById(messageId);
//            if(messageModelOptional.isPresent()) {
//                MessageModel messageModelToUpdate = messageModelOptional.get();
//                messageModelToUpdate.setResponse(response);
//                messageRepository.save(messageModelToUpdate);
//                System.out.println("added message "+messageModelToUpdate.getResponse());
//            }

        } catch (Exception e) {
//            System.err.println("Error processing Kafka message: " + kafkaMessageJson);
            e.printStackTrace();
        }
    }
}
