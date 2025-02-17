package com.consumer.Consumer_Project.service;
import com.consumer.Consumer_Project.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



import java.util.List;
import java.util.concurrent.*;

@Service
public class KafkaMessageConsumerService {


    @Autowired
    MessageRepository messageRepository;

//    String dummyApiUrl = "https://nx51np36-2707.inc1.devtunnels.ms/secured/apigateway/bulkapi";
    String dummyApiUrl = "http://localhost:2707/secured/apigateway/bulkapi";
//      String dummyApiUrl = "http://node-app:2707/secured/apigateway/bulkapi";
//      HttpClient client = HttpClient.newHttpClient();
      RestTemplate apiRestTemplate = new RestTemplate();



    public final ExecutorService executorService = Executors.newFixedThreadPool(10);


    @KafkaListener(topics = "first", containerFactory = "kafkaListenerContainerFactory")
    public void consumeMessage(List<String> kafkaMessage) {
            kafkaMessage.forEach(kafkaMessageJson -> {
                executorService.submit(() ->{
                    try {
                        // Send message to external API and get response
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




















//            ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
//            int threadCount = threadGroup.activeCount();
//            System.out.println("The number of threads are => "+threadCount);
