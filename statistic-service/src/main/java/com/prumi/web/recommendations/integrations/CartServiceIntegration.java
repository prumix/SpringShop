package com.prumi.web.recommendations.integrations;

import com.prumi.web.api.carts.CartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component

public class CartServiceIntegration {
    @Qualifier("Cart")
    @Autowired
    private WebClient cartServiceWebClient;

       public List<CartItemDto> getCarts(){
        return cartServiceWebClient.get()
                .uri("/api/v1/cart/statCart")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CartItemDto>>() {
                })
                .block();
    }

    public void clearCartList(){
           cartServiceWebClient.get()
                   .uri("/api/v1/cart/statCart/clear")
                   .retrieve()
                   .bodyToMono(Boolean.class)
                   .block();
    }

}
