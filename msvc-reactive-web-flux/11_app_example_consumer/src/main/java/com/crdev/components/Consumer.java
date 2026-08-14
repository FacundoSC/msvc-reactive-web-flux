package com.crdev.components;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

public class Consumer {
    private KafkaConsumer<String, String> consumer;

    public Consumer() {
        // Initialize the Kafka consumer here
        Properties props = new Properties();
        String bootstrapServers = System.getenv().getOrDefault("KAFKA_BOOTSTRAP_SERVERS", "localhost:9092");
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", "test-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<>(props);
    }

    public void subscribe(String topic) {
        consumer.subscribe(java.util.Collections.singletonList(topic));
       consumeMessages();
    }

    public void consumeMessages() {
        // encuesto  por nuevos mensajes y luego los procesa
        while (true) {
            consumer.poll(java.time.Duration.ofMillis(100)).forEach(record -> {
                System.out.printf("Consumed message: key = %s, value = %s, offset = %d%n", record.key(), record.value(), record.offset());
            });
        }
    }
}
