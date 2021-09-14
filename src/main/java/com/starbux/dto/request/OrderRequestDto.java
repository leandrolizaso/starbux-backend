package com.starbux.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequestDto {

    Integer drinkId;
    List<Integer> toppingsId;
    String customer;

}
