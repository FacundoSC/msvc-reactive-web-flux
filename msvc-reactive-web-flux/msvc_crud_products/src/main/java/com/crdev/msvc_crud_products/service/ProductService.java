package com.crdev.msvc_crud_products.service;

import com.crdev.msvc_crud_products.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findByCategory(String category);
    Product findByCode(int code);

    void save(Product product);

    Product delete(int code);

    Product updatePriceUnit(int code, double priceUnit);
}
