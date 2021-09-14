package com.starbux.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseDrinkDto implements Serializable {
    //private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Integer price;

}
