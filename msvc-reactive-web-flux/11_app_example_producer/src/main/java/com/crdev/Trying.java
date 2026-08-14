package com.crdev;


import com.crdev.components.Producer;

import java.time.LocalDateTime;
import java.util.List;

public class Trying {


    static void main(String[] args) {
        Producer producer = new Producer();
        String topic = "test-topic";
        List<String> stoicPhilososphers = List.of(
                "Epictetus",
                "Seneca",
                "Marcus Aurelius",
                "Zeno of Citium",
                "Chrysippus",
                "Cleanthes",
                "Musonius Rufus",
                "Hierocles",
                "Gaius Musonius Rufus",
                "Diogenes of Babylon"
        );
        stoicPhilososphers.forEach(stoicPhilosospher -> {
            String message = "Stoic Philosopher: " + stoicPhilosospher + " sent at: " + LocalDateTime.now();
            producer.sendMessage(topic, message);
            System.out.println("Sent: " + message);
            try {
                Thread.sleep(1000); // Sleep for 1 second between messages
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        producer.close();
    }
}
