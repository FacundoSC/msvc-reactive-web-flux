package org.faccordoba.springcloud.msvc.msvc_names_reactive;

import org.faccordoba.springcloud.msvc.msvc_names_reactive.controller.NameController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
class MsvcNamesReactiveApplicationTests {
    @Autowired
    NameController controller;

    @Test
    void contextLoads() {
        StepVerifier.create(controller.getAllNames())
                .expectNext("Alice")
                .expectNext("Bob","Charlie")
                .expectNextCount(2)
                .verifyComplete();
    }

}
