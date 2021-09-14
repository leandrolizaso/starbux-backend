package com.starbux.dto;

import com.starbux.model.entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    Integer id;
    List<ItemDto> drinks;
    Order.Status status;
    Double discount;
    Integer price;
    Double total;
    String customer;

}
