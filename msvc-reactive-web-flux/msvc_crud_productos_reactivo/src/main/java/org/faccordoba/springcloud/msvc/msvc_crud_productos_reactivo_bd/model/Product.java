package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model;

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
