package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(value = "productos")
public class Product {
    @Id
    private  int code;
    private  String name;
    private  String category;
    private  double priceUnit;

}
