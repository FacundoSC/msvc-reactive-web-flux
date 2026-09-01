package com.crdev.msvc_rastrear.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Element {
    private  int code;
    private  String name;
    private  String category;
    private  double priceUnit;
    private  String  store;
}
