package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo.service;


import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ProductService {
    Flux<Product> findAll();
    Flux<Product> findByCategory(String category);
    Mono<Product> findByCode(int code);
    Mono<Void> save(Product product);
    Mono<Product> delete(int code);
    Mono<Product> updatePriceUnit(int code, double priceUnit);
}
