package com.crdev.msvc_crud_products.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    private  int code;
    private  String name;
    private  String category;
    private  double priceUnit;

}
