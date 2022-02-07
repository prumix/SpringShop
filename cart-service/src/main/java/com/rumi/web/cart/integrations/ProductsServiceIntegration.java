package com.rumi.web.cart.integrations;

import com.prumi.web.api.core.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    private final WebClient productServiceWebClient;



    public Optional<ProductDto> findById(Long productId) {
        return productServiceWebClient.get()
                .uri("/api/v1/products/{productId}",productId)
              //  .header("productId", String.valueOf(productId))
                .retrieve()
                .bodyToMono(ProductDto.class)
                .blockOptional();
    }
}
