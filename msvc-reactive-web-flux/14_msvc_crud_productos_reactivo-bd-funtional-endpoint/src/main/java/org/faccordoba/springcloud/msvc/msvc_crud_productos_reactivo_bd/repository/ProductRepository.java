package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.repository;

import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.domain.Product;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<Product,Integer> {

    Flux<Product> findByCategory(String category);

    @Transactional
    @Modifying
    Mono<Void> deleteByName(String name);
}
