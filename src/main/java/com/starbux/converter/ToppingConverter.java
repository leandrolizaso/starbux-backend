package com.starbux.converter;

import com.starbux.dto.ToppingDto;
import com.starbux.model.entity.Topping;

public class ToppingConverter {

    public static ToppingDto toToppingDto(Topping topping) {
        ToppingDto dto = new ToppingDto();
        dto.setId(topping.getId());
        dto.setName(topping.getName());
        dto.setPrice(topping.getPrice());

        return dto;
    }

    public static Topping toTopping(ToppingDto toppingDto) {
        Topping topping = new Topping();
        topping.setId(toppingDto.getId());
        topping.setName(toppingDto.getName());
        topping.setPrice(toppingDto.getPrice());
        return topping;
    }
}
