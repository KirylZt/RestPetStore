package com.example.controller;

import com.example.entity.Order;
import com.example.entity.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/store")
public class StoreController {
    private final List<Order> orders = new ArrayList<>();

    @PostMapping("/add")
    public void addOrder(@RequestBody Order order){
        orders.add(order);
    }

    @GetMapping("/getByStatus/{orderStatus}")
    public ResponseEntity<List<Order>> getOrderByStatus(@PathVariable String orderStatus){
        List<Order> result = orders.stream()
                .filter(s -> s.getStatus().equals(OrderStatus.valueOf(orderStatus.toUpperCase(Locale.ENGLISH))))
                .collect(Collectors.toList());
        if (result.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/delete/{orderId}")
    public void deleteById(@PathVariable long orderId){
        orders.removeIf(o -> o.getId() == orderId);
    }


    @GetMapping("/getById/{orderId}")
    public ResponseEntity<Order> getOrderByStatus(@PathVariable long orderId){
        for (Order order: orders){
            if (order.getId() == orderId) {
                return ResponseEntity.ok(order);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
