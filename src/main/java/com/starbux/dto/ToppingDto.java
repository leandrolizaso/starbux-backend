package com.starbux.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class ToppingDto implements Serializable {
    Integer id;
    String name;
    Integer price;
}
