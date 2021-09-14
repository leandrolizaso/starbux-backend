package com.starbux.converter;

import com.starbux.dto.BaseDrinkDto;
import com.starbux.model.entity.BaseDrink;

public class BaseDrinkConverter {

    public static BaseDrinkDto toBaseDrinkDto(BaseDrink drink){
        BaseDrinkDto dto = new BaseDrinkDto();

        dto.setName(drink.getName());
        dto.setPrice(drink.getPrice());
        dto.setId(drink.getId());

        return dto;
    }
}
