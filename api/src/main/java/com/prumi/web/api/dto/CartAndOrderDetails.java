package com.prumi.web.api.dto;


import lombok.Data;

@Data
public class CartAndOrderDetails {
    private OrderDetailsDto orderDetailsDto;
    private CartDto cartDto;

    public CartAndOrderDetails() {
    }

    public CartAndOrderDetails(OrderDetailsDto orderDetailsDto, CartDto cartDto) {
        this.orderDetailsDto = orderDetailsDto;
        this.cartDto = cartDto;
    }


}
