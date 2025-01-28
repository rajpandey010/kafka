package com.consumer.Consumer_Project.service;
import com.consumer.Consumer_Project.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class KafkaMessageConsumerService {

//    Logger LOG = LoggerFactory.getLogger(KafkaMessageConsumerService.class);
    @Autowired
    MessageRepository messageRepository;
//    Date date = new Date();
//    int intial =  date.getSeconds();

//    String dummyApiUrl = "https://nx51np36-2707.inc1.devtunnels.ms/secured/apigateway/bulkapi";
//    String dummyApiUrl = "http://localhost:2707/secured/apigateway/bulkapi";
      String dummyApiUrl = "http://node-app:2707/secured/apigateway/bulkapi";
      HttpClient client = HttpClient.newHttpClient();
    public final ExecutorService executorService = Executors.newFixedThreadPool(10);


    @KafkaListener(topics = "first", containerFactory = "kafkaListenerContainerFactory")
    public void consumeMessage(List<String> kafkaMessage) {
//        System.out.println("Size of the arrays = "+kafkaMessage.size());
//        if(intial <= intial+2){
//            System.exit(0);
//        }
        executorService.submit(() -> {
            kafkaMessage.forEach(kafkaMessageJson -> {
                executorService.submit(() ->{
                    try {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(dummyApiUrl))
                                .header("Content-Type", "application/json")
                                .POST(HttpRequest.BodyPublishers.ofString(kafkaMessageJson))
                                .build();
                        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
        });
    }
}
