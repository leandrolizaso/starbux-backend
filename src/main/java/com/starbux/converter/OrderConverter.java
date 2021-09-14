package com.starbux.converter;

import com.starbux.dto.OrderDto;
import com.starbux.model.entity.Order;
import java.util.stream.Collectors;

public class OrderConverter {

    public static OrderDto toOrderDto(Order order){
        OrderDto dto = new OrderDto();

        dto.setCustomer(order.getCustomer());
        dto.setId(order.getId());
        dto.setPrice(order.getPrice());
        dto.setDiscount(order.getDiscount());
        dto.setStatus(order.getStatus());
        dto.setTotal(order.getTotal());
        dto.setDrinks(order.getItems().stream().map(ItemConverter::toItemDto).collect(Collectors.toList()));

        return dto;
    }
}
