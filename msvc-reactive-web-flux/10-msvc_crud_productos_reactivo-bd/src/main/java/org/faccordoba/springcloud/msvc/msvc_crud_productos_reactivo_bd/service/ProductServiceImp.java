package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.service;

import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model.Product;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService{
    private final ProductRepository productosRepository;
    private static final List<Product> products = new ArrayList<>(List.of(
            new Product(1, "Laptop", "Electronics",  999.99),
            new Product(2, "Smartphone", "Electronics", 799.99),
            new Product(3, "Headphones", "Accessories",  199.99),
            new Product(4, "Coffee Maker", "Home Appliances",  49.99)
    ));

    public ProductServiceImp(ProductRepository productosRepository) {
        this.productosRepository = productosRepository;
    }


    @Override
    public Flux<Product> findAll() {
        return productosRepository.findAll();
    }

    @Override
    public Flux<Product> findByCategory(String category) {
        return  productosRepository.findByCategory(category);
    }

    @Override
    public Mono<Product> findByCode(int code) {
        return productosRepository.findById(code);
    }

    @Override
    public Mono<Void> save(Product product) {
        return findByCode(product.getCode()) //Mono<Product>
                .switchIfEmpty(Mono.fromRunnable(() -> {
                    System.out.println("Saving new product: " + product);
                    productosRepository.save(product).subscribe();
                })) //Mono<Producto>
                .then();
    }

    @Override
    public Mono<Product> delete(int code) {
        return findByCode(code) //Mono<Product>
                .flatMap(product -> {
                    return productosRepository.delete(product).thenReturn(product);
                }); //Mono<Product>
    }

    @Override
    public Mono<Product> updatePriceUnit(int code, double priceUnit) {
        return findByCode(code) //Mono<Product>
                .flatMap(product -> {
                    product.setPriceUnit(priceUnit);
                    return productosRepository.save(product);
                }); //Mono<Product>
    }
}
