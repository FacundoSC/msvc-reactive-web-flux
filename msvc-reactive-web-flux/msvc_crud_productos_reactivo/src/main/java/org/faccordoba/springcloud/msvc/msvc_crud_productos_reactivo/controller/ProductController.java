package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo.controller;

import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo.domain.Product;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/")
    public Flux<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping(value = "/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Product> getAllProductsByCategory(@PathVariable String category ) {
         return productService.findByCategory(category);
     }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE  )
    public Mono<Product> getProductByCode(@PathVariable("code") int code) {
         return productService.findByCode(code);
     }
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> save(@RequestBody Product product ) {
            return productService.save(product);
    }

    @DeleteMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Product> delete(@PathVariable int code) {
        return productService.delete(code);
    }

    @PatchMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Product> update(@PathVariable int code, @RequestBody Product product) {
        return productService.updatePriceUnit(code, product.getPriceUnit());
    }

}
