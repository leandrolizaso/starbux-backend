package com.starbux.converter;

import com.starbux.dto.DrinkDto;
import com.starbux.model.entity.Drink;

public class DrinkConverter {

    public static DrinkDto toDrinkDto(Drink drink){
        DrinkDto dto = new DrinkDto();

        dto.setName(drink.getName());
        dto.setPrice(drink.getPrice());
        dto.setId(drink.getId());

        return dto;
    }
}
