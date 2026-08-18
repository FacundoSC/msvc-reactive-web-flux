package org.faccordoba.springcloud.msvc.msvc_orders.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
 private int code;
 private String name;
 private int units;
 private String address;

}
