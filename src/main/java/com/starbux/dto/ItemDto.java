package com.starbux.dto;

import com.starbux.model.entity.Drink;
import lombok.Data;

import java.util.List;

@Data
public class ItemDto {

    Integer id;
    Drink drink;
    Integer totalPrice;
    List<ToppingDto> toppings;

}
