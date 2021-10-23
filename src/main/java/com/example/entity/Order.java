package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order{
    private long id;
    private long petId;
    private int quantity;
    private Date shipDate;
    OrderStatus status;
    private boolean complete;
}