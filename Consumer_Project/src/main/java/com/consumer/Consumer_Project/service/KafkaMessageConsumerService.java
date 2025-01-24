package com.consumer.Consumer_Project.service;

import com.consumer.Consumer_Project.modal.MessageModel;
import com.consumer.Consumer_Project.repository.MessageRepository;
import com.consumer.Consumer_Project.dto.KafkaMessage; // Renamed DTO for clarity
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessageConsumerService {

//    Logger LOG = LoggerFactory.getLogger(KafkaMessageConsumerService.class);
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
            HttpClient client = HttpClient.newHttpClient();

            // Extract UUID and message content
            String messageId = messagePayload.getUuid();
            String receivedMessage = messagePayload.getMessage();
//            System.out.println("receivedMessage "+receivedMessage);

            // Save message data to the database
//            MessageModel messageModel = new MessageModel(messageId, receivedMessage, null);
//            messageRepository.save(messageModel);

            // Send message to external API and get response
            // Construct the HTTP Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(dummyApiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(kafkaMessageJson)) // Set JSON body
                    .build();

            // Send the HTTP Request to the Kafka Producer
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            response.thenApply(HttpResponse::body).thenAccept(System.out::println);

//            RestTemplate apiRestTemplate = new RestTemplate();
//            String response = apiRestTemplate.postForObject(dummyApiUrl, receivedMessage, String.class);

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
