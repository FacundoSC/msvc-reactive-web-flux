package org.faccordoba.springcloud.msvc.msvc_orders.controller;

import org.faccordoba.springcloud.msvc.msvc_orders.model.Order;
import org.faccordoba.springcloud.msvc.msvc_orders.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    public  OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> sendOrder(@RequestBody Order order) {
        try{
        orderService.createOrder(order);
        return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}
