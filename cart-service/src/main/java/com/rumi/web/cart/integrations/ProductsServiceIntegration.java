package com.rumi.web.cart.integrations;

import com.prumi.web.api.core.ProductDto;
import com.prumi.web.api.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    private final WebClient productServiceWebClient;



    public Optional<ProductDto> findById(Long productId) {
        return productServiceWebClient.get()
                .uri("/api/v1/products/{productId}",productId)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new ResourceNotFoundException("Product-MS not working")))
                .onStatus(HttpStatus::is4xxClientError,clientResponse -> Mono.error(new ResourceNotFoundException("Product not found, id = " + productId)))
                .bodyToMono(ProductDto.class)
                .blockOptional();
    }
}
