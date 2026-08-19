package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class MsvcCrudProductosReactivoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcCrudProductosReactivoApplication.class, args);
    }

}
