package org.faccordoba.springcloud.msvc.msvc_names_reactive.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.util.List;

@Configuration
public class NameController {

    @Bean
    public RouterFunction<ServerResponse> getAllNames() {
        List<String> names = List.of("Alice", "Bob", "Charlie", "David", "Eve");
        return RouterFunctions.route(RequestPredicates.GET("/names"),
                request -> ServerResponse.ok() //BodyBuilder
                        .contentType(MediaType.APPLICATION_JSON) //BodyBuilder
                        .body(Flux.fromIterable(names), String.class) //Mono<ServerResponse>
                ); //RouterFunction<ServerResponse>
    }


}
