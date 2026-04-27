package com.crdev.msvc_crud_products.service;

import com.crdev.msvc_crud_products.domain.Product;
import org.springframework.stereotype.Service;

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
    public List<Product> findAll() {
        return products;
    }

    @Override
    public List<Product> findByCategory(String category) {
        return products.stream().filter(product -> product.getCategory().equals(category))
                .toList();

    }

    @Override
    public Product findByCode(int code) {
        return products.stream().filter(product -> product.getCode() == code)
                .findFirst().orElse(null);
    }

    @Override
    public void save(Product product) {
        if (findByCode(product.getCode()) != null) {
            System.out.println("Product with code " + product.getCode() + " already exists. Skipping save.");
        }
        products.add(product);
    }

    @Override
    public Product delete(int code) {
        Product product = findByCode(code);
        if (product != null) {
            products.removeIf(p -> p.getCode() == code);
            return product;
        }
        return null;


    }

    @Override
    public Product updatePriceUnit(int code, double priceUnit) {
        Product product = findByCode(code);
        if (product != null) {
            product.setPriceUnit(priceUnit);
            return product;
        }
        return null;
    }
}
