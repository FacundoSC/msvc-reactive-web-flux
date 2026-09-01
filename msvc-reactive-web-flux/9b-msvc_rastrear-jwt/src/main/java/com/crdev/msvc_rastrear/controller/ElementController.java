package com.crdev.msvc_rastrear.controller;

import com.crdev.msvc_rastrear.domain.Element;
import com.crdev.msvc_rastrear.service.ElementsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController(value = "/")
public class ElementController {
    ElementsService service;

    public ElementController(ElementsService service){
        this.service= service;
    }

    @GetMapping(value = "elements/{price}")
    public ResponseEntity<Flux<Element>> getElementsByMaxPrice(@PathVariable(value = "price") double price){
        return ResponseEntity.ok(service.elementsMaxPrice(price));
    }

}
