package com.crdev.testcrudproductsspring.runners;

import com.crdev.testcrudproductsspring.domain.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class TestRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        String url = "http://localhost:8083";
        WebClient client = WebClient.create(url);

       /*   listar los productos

        String path = "/api/v1/products/";
        Flux <Product>  flux = client.get()//RequestHeadersUriSpec
                .uri(path)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Product.class); //Flux<Product>
        flux.subscribe(System.out::println);
    */



         String path  = "/api/v1/products/";

       Mono<Product> productMono =  Mono.just(new Product(5, "prueba","Electronics", 20.00 ));

       client.post()
                .uri(path)
                .body(productMono, Product.class)
                .retrieve()
               /* .onStatus(HttpStatusCode::is4xxClientError , response ->
                                response.bodyToMono(String.class).flatMap(errorBody ->
                                        Mono.error(new RuntimeException("Client error: " ))))
               .onStatus(HttpStatusCode::is5xxServerError , response ->
                       response.bodyToMono(String.class).flatMap(errorBody ->
                               Mono.error(new RuntimeException("Client Server: " ))))*/
               .bodyToMono(Void.class)
               .block();

        Flux <Product>  flux = client.get()//RequestHeadersUriSpec
                .uri(path)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Product.class); //Flux<Product>
        flux.subscribe(System.out::println);


                 Mono<Product> monoFind= client.delete()
                                .uri(path+"/"+40)
                                .retrieve()
                                .onStatus(h -> h.is4xxClientError(), t ->{
                                    System.out.println("No se encontro el registro");
                                    return Mono.empty();
                                })
                                .bodyToMono(Product.class);

                 monoFind.subscribe(System.out::println);



    }
}
