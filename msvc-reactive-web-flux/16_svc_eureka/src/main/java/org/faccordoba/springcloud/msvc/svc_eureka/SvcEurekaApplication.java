package org.faccordoba.springcloud.msvc.svc_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SvcEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SvcEurekaApplication.class, args);
    }

}
