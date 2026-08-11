package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.service;

import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.domain.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService{
    private static final List<Product> products = new ArrayList<>(List.of(
            new Product(1, "Laptop", "Electronics",  999.99),
            new Product(2, "Smartphone", "Electronics", 799.99),
            new Product(3, "Headphones", "Accessories",  199.99),
            new Product(4, "Coffee Maker", "Home Appliances",  49.99)
    ));


    @Override
    public Flux<Product> findAll() {
        return Flux.fromIterable(products)
                .delayElements(java.time.Duration.ofSeconds(2));
    }

    @Override
    public Flux<Product> findByCategory(String category) {
        return findAll().filter(p -> p.getCategory().equalsIgnoreCase(category));
    }

    @Override
    public Mono<Product> findByCode(int code) {
        return  findAll().filter(p -> p.getCode() == code) //Flux<Product>
                .next() //Mono<Product>
                .delayElement(java.time.Duration.ofSeconds(2));
    }

    @Override
    public Mono<Void> save(Product product) {
        return findByCode(product.getCode()) //Mono<Product>
                .switchIfEmpty(Mono.fromRunnable(() -> {
                    System.out.println("Saving new product: " + product);
                    products.add(product);
                })) //Mono<Producto>
                .then();
    }

    @Override
    public Mono<Product> delete(int code) {
        return findByCode(code) //Mono<Product>
                .map(product -> {
                    products.removeIf(p -> p.getCode() == code);
                    return product;
                });
    }

    @Override
    public Mono<Product> updatePriceUnit(int code, double priceUnit) {
        return findByCode(code)
                .map(product -> {
                    product.setPriceUnit(priceUnit);
                    return product;
                });
    }
}
