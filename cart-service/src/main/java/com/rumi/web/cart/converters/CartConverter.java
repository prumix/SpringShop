package com.rumi.web.cart.converters;

import com.prumi.web.api.dto.CartDto;
import com.rumi.web.cart.dto.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartConverter {
    public CartDto entityToDto(Cart cart){
        return new CartDto(cart.getItems(),cart.getTotalPrice());
    }
}
