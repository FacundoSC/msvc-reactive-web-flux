package com.crdev;


import com.crdev.components.Consumer;

public class Catching {
    static void main() {
        Consumer consumer = new Consumer();
        String topic = "test-topic";
        consumer.subscribe(topic);
    }
}
