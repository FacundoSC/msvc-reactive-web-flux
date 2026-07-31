package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo.controller;

import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo.domain.Product;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Flux<Product>> findAll() {
        System.out.println("method all");
        return  ResponseEntity.ok(productService.findAll());
    }

    @PostMapping(value = "/")
    public ResponseEntity<Mono<Void>> save(@RequestBody Product product ) {
        System.out.println("metodo save");
        return  ResponseEntity.ok(productService.save(product));
    }

    @GetMapping(value = "/category/{category}")
    public ResponseEntity<Flux<Product>> getAllProductsByCategory(@PathVariable String category ) {
         return  ResponseEntity.ok(productService.findByCategory(category));
     }

    @GetMapping(value = "/{code}")
    public ResponseEntity<Mono<Product>> getProductByCode(@PathVariable("code") int code) {
        return ResponseEntity.ok(productService.findByCode(code));
     }


    @DeleteMapping(value = "/{code}")
    public Mono<ResponseEntity<Product>> delete(@PathVariable int code) {
         return productService.delete(code)
                 .map(ResponseEntity::ok)
                 .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PatchMapping(value = "/{code}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<Product>> update(@PathVariable int code, @RequestBody Product product) {
        return productService.updatePriceUnit(code, product.getPriceUnit())
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
