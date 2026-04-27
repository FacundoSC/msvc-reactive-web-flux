package com.crdev.msvc_crud_products.controller;

import com.crdev.msvc_crud_products.domain.Product;
import com.crdev.msvc_crud_products.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping(value = "/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getAllProductsByCategory(@PathVariable String category ) {
        return productService.findByCategory(category);
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE  )
    public Product getProductByCode(@PathVariable("code") int code) {
        return productService.findByCode(code);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody Product product ) {
        productService.save(product);
    }

    @DeleteMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product delete(@PathVariable int code) {
       return productService.delete(code);
    }

    @PatchMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product update(@PathVariable int code, @RequestBody Product product) {
        return productService.updatePriceUnit(code, product.getPriceUnit());
    }

}
