package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.controller;

import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model.Product;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Configuration
public class ProductController {
    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @Bean
    public RouterFunction<ServerResponse> productRoutes() {
        String basePath = "/api/v1/products";
        return RouterFunctions.route()
                .GET(basePath, request -> {
                    logger.info("Finding all products");
                    Flux<Product> products = productService.findAll();
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(products, Product.class);
                })
                .GET(basePath + "/category/{category}", request -> {
                    String category = request.pathVariable("category");
                    logger.info("Finding products by category: {}", category);
                    Flux<Product> products = productService.findByCategory(category);
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(products, Product.class);
                })
                .GET(basePath + "/{code}", request -> {
                    int code = Integer.parseInt(request.pathVariable("code"));
                    logger.info("Finding product by code: {}", code);
                    Mono<Product> productMono = productService.findByCode(code);
                    return productMono.flatMap(product -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(product))
                            .switchIfEmpty(ServerResponse.notFound().build());
                })
                .POST(basePath, request -> {
                    logger.info("Saving product");
                    Mono<Product> productMono = request.bodyToMono(Product.class);
                    return productMono.flatMap(product -> {
                        Mono<Void> saveMono = productService.save(product);
                        return saveMono.then(ServerResponse.ok().build());
                    });
                })
                .DELETE(basePath + "/{code}", request -> {
                    int code = Integer.parseInt(request.pathVariable("code"));
                    logger.info("Deleting product by code: {}", code);
                    Mono<Product> deleteMono = productService.delete(code);
                    return deleteMono.then(ServerResponse.ok().build())
                            .switchIfEmpty(ServerResponse.notFound().build());
                })
                .PATCH(basePath + "/{code}", request -> {
                    int code = Integer.parseInt(request.pathVariable("code"));
                    logger.info("Updating product by code: {}", code);
                    Mono<Product> productMono = request.bodyToMono(Product.class);
                    return productMono.flatMap(product -> {
                        Mono<Product> updateMono = productService.updatePriceUnit(code, product.getPriceUnit());
                        return updateMono.flatMap(updatedProduct -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(updatedProduct))
                                .switchIfEmpty(ServerResponse.notFound().build());
                    });
                })
                .build();
    }

    @Bean
    CorsWebFilter corsWebFilter() {
        return new CorsWebFilter(exchange -> {
            CorsConfiguration corsConfig = new org.springframework.web.cors.CorsConfiguration();
            corsConfig.addAllowedOrigin("*");
            corsConfig.addAllowedMethod("*");
            corsConfig.addAllowedHeader("*");
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", corsConfig);
            return corsConfig;
        });
    }
}