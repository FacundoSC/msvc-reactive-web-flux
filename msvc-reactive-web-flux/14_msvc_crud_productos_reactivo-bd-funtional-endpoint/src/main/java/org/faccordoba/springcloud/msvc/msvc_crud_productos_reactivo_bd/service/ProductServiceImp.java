package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model.Order;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model.Product;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductServiceImp implements ProductService , StockService {
    private final ProductRepository productsRepository;
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);


    public ProductServiceImp(ProductRepository productRepository) {
        this.productsRepository = productRepository;
    }


    @Override
    public Flux<Product> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public Flux<Product> findByCategory(String category) {
        return  productsRepository.findByCategory(category);
    }

    @Override
    public Mono<Product> findByCode(int code) {
        Mono<Product> p = productsRepository.findById(code);
        return p;
    }

    @Override
    public Mono<Void> save(Product product) {
        return findByCode(product.getCode()) //Mono<Product>
                .switchIfEmpty(Mono.fromRunnable(() -> {
                    System.out.println("Saving new product: " + product);
                    productsRepository.save(product).subscribe();
                })) //Mono<Producto>
                .then();
    }

    @Override
    public Mono<Product> delete(int code) {
        return findByCode(code) //Mono<Product>
                .flatMap(product -> {
                    return productsRepository.delete(product).thenReturn(product);
                }); //Mono<Product>
    }

    @Override
    public Mono<Product> updatePriceUnit(int code, double priceUnit) {
        return findByCode(code) //Mono<Product>
                .flatMap(product -> {
                    product.setPriceUnit(priceUnit);
                    return productsRepository.save(product);
                }); //Mono<Product>
    }






    @Override
    @KafkaListener(topics = "${spring.kafka.topic.name}", containerFactory = "kafkaListenerContainerFactory", groupId = "group1")
    public void updateStock(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Order order = mapper.readValue(json, Order.class);
            logger.info("order: {}", order);
            findByCode(order.getCode()) //Mono<Product>
                    .flatMap( product -> {
                        product.setStock(product.getStock() - order.getUnits());
                        return productsRepository.save(product);
                    }) //Mono<Product>
                    .switchIfEmpty(Mono.empty())
                    .subscribe();
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            logger.error("Failed to parse order JSON", e);
        }
    }
}
