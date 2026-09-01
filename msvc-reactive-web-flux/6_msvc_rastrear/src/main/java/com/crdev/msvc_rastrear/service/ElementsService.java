package com.crdev.msvc_rastrear.service;

import com.crdev.msvc_rastrear.domain.Element;
import reactor.core.publisher.Flux;

public interface ElementsService {
    Flux<Element> elementsMaxPrice(double price);
}
