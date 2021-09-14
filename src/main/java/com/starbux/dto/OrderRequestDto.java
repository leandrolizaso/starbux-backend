package com.starbux.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {

    Integer drinkId;
    List<Integer> toppingsId;
    String customer;

}
