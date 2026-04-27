package com.crdev.msvc_crud_products.controller;

import com.crdev.msvc_crud_products.domain.Product;
import com.crdev.msvc_crud_products.service.ProductService;

import java.util.List;

@RestController("api/v1")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

     @GetMapping("/products")
     public List<Product> getAllProducts() {
         return productService.findAll();
     }


     @GetMapping("/products/category/{category}")
     public List<Product> getAllProductsByCategory(@PathVariable String category) {
         return productService.findByCategory(category);
     }

     @GetMapping("/products")
     public Product getProductByCode(@RequestParam ("code")int code) {
         return productService.findByCode(code);
     }
}
