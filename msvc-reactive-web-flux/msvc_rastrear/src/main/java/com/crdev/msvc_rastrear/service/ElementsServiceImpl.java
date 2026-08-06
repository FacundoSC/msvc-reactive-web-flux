package com.crdev.msvc_rastrear.service;

import com.crdev.msvc_rastrear.domain.Element;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Base64;

@Service
public class ElementsServiceImpl implements  ElementsService{
    private final String urlFirstStore ="http://localhost:8084";
    private final String urlSecondStore ="http://localhost:8093";

    @Override
    public Flux<Element> elementsMaxPrice(double price) {
        Flux<Element> fluxFirstStore= getElements(urlFirstStore,"tienda-1");
        Flux<Element> fluxSecondStore= getElements(urlSecondStore,"tienda-2");
        return Flux.merge(fluxFirstStore, fluxSecondStore)
                .filter( element -> element.getPriceUnit()<= price);
    }



    private Flux<Element> getElements(String url, String store){
        WebClient webClient = WebClient.create(url);
        return webClient
                .get()
                .uri("/api/v1/products/")
                .accept(MediaType.APPLICATION_JSON)
           //     .header("Authorization", "Basic "+getEncoderBase64Credentials("user1", "user1"))
                .retrieve()
                .bodyToFlux(Element.class)
                .map(element -> {
                    element.setStore(store);
                     return element;
                });
    }


    private String getEncoderBase64Credentials(String user, String pwd) {
        String credencial = user + ":" + pwd;
        return Base64.getEncoder().encodeToString(credencial.getBytes());
    }
}

