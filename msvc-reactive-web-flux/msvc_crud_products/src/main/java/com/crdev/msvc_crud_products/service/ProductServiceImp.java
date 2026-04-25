package com.crdev.msvc_crud_products.service;

import com.crdev.msvc_crud_products.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService{
    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public Product findByCategory(String category) {
        return null;
    }

    @Override
    public Product findByCode(int code) {
        return null;
    }

    @Override
    public void save(Product product) {

    }

    @Override
    public Product delete(int code) {
        return null;
    }

    @Override
    public Product updatePriceUnit(int code, double priceUnit) {
        return null;
    }
}
