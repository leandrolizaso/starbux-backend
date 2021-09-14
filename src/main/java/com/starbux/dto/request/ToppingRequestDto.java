package com.starbux.dto.request;

import lombok.Data;
import java.io.Serializable;

@Data
public class ToppingRequestDto  implements Serializable {
    String name;
    Integer price;
}
