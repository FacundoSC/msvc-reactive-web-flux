package com.crdev.msvc_rastrear.service;

import com.crdev.msvc_rastrear.domain.Element;
import com.crdev.msvc_rastrear.domain.UserLogin;
import io.netty.util.internal.StringUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Service
public class ElementsServiceImpl implements  ElementsService{
    private final String urlFirstStore ="http://localhost:8085";
    private final String urlSecondStore ="http://localhost:8093";
    private String token;

    @PostConstruct
    public void init(){
        String credential ="user1";
        UserLogin userLogin = new UserLogin(credential, credential);
        loadToken(urlFirstStore,userLogin)
                .subscribe(s -> token=s);
    }


    private Mono<String>loadToken(String url, UserLogin userLogin){
        return WebClient
                .create(url)
                .post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN)
                .bodyValue(userLogin)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Flux<Element> elementsMaxPrice(double price) {
        Flux<Element> fluxFirstStore= getElements(urlFirstStore,"tienda-1", token);
        Flux<Element> fluxSecondStore= getElements(urlSecondStore,"tienda-2", StringUtil.EMPTY_STRING);
        return Flux.merge(fluxFirstStore, fluxSecondStore)
                .filter( element -> element.getPriceUnit()<= price);
    }



    private Flux<Element> getElements(String url, String store, String token){
        String authorization = (StringUtil.isNullOrEmpty(token))?StringUtil.EMPTY_STRING : "Bearer "+token;

        return WebClient
                .create(url)
                .get()
                .uri("/api/v1/products/")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", authorization)
                .retrieve()
                .bodyToFlux(Element.class)
                .map(element -> {
                    element.setStore(store);
                     return element;
                });
    }


}

