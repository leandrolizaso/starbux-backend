package com.starbux.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class DrinkDto implements Serializable {
    private Integer id;
    private String name;
    private Integer price;

}
