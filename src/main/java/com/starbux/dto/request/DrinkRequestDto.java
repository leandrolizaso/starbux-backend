package com.starbux.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class DrinkRequestDto implements Serializable {
    private String name;
    private Integer price;

}
