package org.faccordoba.springcloud.msvc.svc_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator tienda2Routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("root-products", r -> r
                        .path("/")
                        .filters(f -> f.setPath("/api/v1/products/"))
                        .uri("http://localhost:8093"))
                .route("msvc_crud_productos_reactivo_tienda_2", r -> r
                        .path("/api/v1/products/**")
                        .filters(f -> f.rewritePath(
                                "/api/v1/products/(?<segment>.*)",
                                "/api/v1/products/${segment}"
                        ))
                        .uri("http://localhost:8093"))
                .build();
    }
}
