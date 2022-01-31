package com.prumi.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartDto {
    private List<OrderItemDto> items;
    private int totalPrice;

    public CartDto() {
    }
}
