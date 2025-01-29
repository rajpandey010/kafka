package com.consumer.Consumer_Project.service;
import com.consumer.Consumer_Project.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    Date date = new Date();
    int initial =  date.getSeconds();

    @KafkaListener(topics = "first", containerFactory = "kafkaListenerContainerFactory")
    public void consumeMessage(List<String> kafkaMessage) {
            ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
            int threadCount = threadGroup.activeCount();
            System.out.println("The number of threads are => "+threadCount);
            System.out.println(Thread.currentThread());
            kafkaMessage.forEach(kafkaMessageJson -> {
                executorService.submit(() ->{
                    try {

                        // Send message to external API and get response
                        RestTemplate apiRestTemplate = new RestTemplate();
                        String response = apiRestTemplate.postForObject(dummyApiUrl, "", String.class);

//                        HttpRequest request = HttpRequest.newBuilder()
//                                .uri(URI.create(dummyApiUrl))
//                                .header("Content-Type", "application/json")
//                                .POST(HttpRequest.BodyPublishers.ofString(kafkaMessageJson))
//                                .build();
//                            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
//                            response.thenApply(HttpResponse::body).thenAccept(System.out::println);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
    }
}
