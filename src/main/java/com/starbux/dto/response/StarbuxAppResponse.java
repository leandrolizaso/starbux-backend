package com.starbux.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarbuxAppResponse<T> implements Serializable {

    private T data;
    private String error;

}
