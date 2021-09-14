package com.starbux.converter;

import com.starbux.dto.ItemDto;
import com.starbux.model.entity.Item;
import java.util.stream.Collectors;

public class ItemConverter {

    public static ItemDto toItemDto(Item item){
        ItemDto dto = new ItemDto();

        dto.setId(item.getId());
        dto.setDrink(item.getDrink());
        dto.setTotalPrice(item.getTotalPrice());
        dto.setToppings(item.getToppings().stream().map(ToppingConverter::toToppingDto).collect(Collectors.toList()));

        return dto;
    }

    public static Item toItem(ItemDto itemDto){
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setToppings(itemDto.getToppings().stream().map(ToppingConverter::toTopping).collect(Collectors.toSet()));
        item.setDrink(itemDto.getDrink());
        return item;
    }
}
