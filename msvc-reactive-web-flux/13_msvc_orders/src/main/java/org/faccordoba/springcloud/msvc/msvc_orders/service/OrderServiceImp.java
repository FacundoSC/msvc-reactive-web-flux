package org.faccordoba.springcloud.msvc.msvc_orders.service;

import org.faccordoba.springcloud.msvc.msvc_orders.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;
    @Value("${spring.kafka.topic.name}")
    private String topic;
    private final Logger logger = LoggerFactory.getLogger(OrderServiceImp.class);



    @Override
    public void createOrder(Order order) {
        CompletableFuture<SendResult<String, Order>> future = kafkaTemplate.send(topic, order);
        future.whenCompleteAsync((result, ex) -> {
            if (ex != null) {
                logger.error("Error sending order: {}", ex.getMessage());
                throw new RuntimeException("Error sending message: " + ex.getMessage());
            } else {
                logger.info("Message sent successfully: {}", result.getRecordMetadata().toString());
            }
        });
    }
}
