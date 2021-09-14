package com.starbux.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class CustomerDto implements Serializable {
    String username;
    double totalAmount;
}
