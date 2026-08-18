package org.faccordoba.springcloud.msvc.msvc_orders.service;

import org.faccordoba.springcloud.msvc.msvc_orders.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class OrderServiceImp implements OrderService {
    @Value("${spring.kafka.topic.name}")
    private String topicName;
    @Autowired
    private KafkaTemplate<String,  Order> kafkaTemplate;

    @Override
    public void createOrder(Order order) {
     CompletableFuture<SendResult<String, Order>> future = kafkaTemplate.send(topicName, order);
      future.whenCompleteAsync((result, ex) -> {
          if (ex != null)
              throw new RuntimeException("Error sending message: " + ex.getMessage());
          System.out.println("Message sent successfully: " + result.getRecordMetadata().toString());

      });
    }
}
