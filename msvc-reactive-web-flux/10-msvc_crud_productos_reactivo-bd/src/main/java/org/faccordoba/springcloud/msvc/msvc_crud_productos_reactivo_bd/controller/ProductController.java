package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.controller;

import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.domain.Product;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<Flux<Product>> findAll() {
        logger.info("Finding all products");
        return  ResponseEntity.ok(productService.findAll());
    }

    @PostMapping(value = "/")
    public ResponseEntity<Mono<Void>> save(@RequestBody Product product ) {
        logger.info("Saving product");
        return  ResponseEntity.ok(productService.save(product));
    }

    @GetMapping(value = "/category/{category}")
    public ResponseEntity<Flux<Product>> getAllProductsByCategory(@PathVariable String category ) {
        logger.info("Finding products by category: {}", category);
        return  ResponseEntity.ok(productService.findByCategory(category));
     }

    @GetMapping(value = "/{code}")
    public ResponseEntity<Mono<Product>> getProductByCode(@PathVariable("code") int code) {
        logger.info("Finding product by code: {}", code);
        return ResponseEntity.ok(productService.findByCode(code));
     }


    @DeleteMapping(value = "/{code}")
    public Mono<ResponseEntity<Product>> delete(@PathVariable int code) {
        logger.info("Deleting product by code: {}", code);
         return productService.delete(code)
                 .map(ResponseEntity::ok)
                 .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PatchMapping(value = "/{code}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<Product>> update(@PathVariable int code, @RequestBody Product product) {
        logger.info("Updating product by code: {}", code);
        return productService.updatePriceUnit(code, product.getPriceUnit())
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
