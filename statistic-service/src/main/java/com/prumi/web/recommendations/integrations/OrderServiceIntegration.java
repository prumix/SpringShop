package com.prumi.web.recommendations.integrations;

import com.prumi.web.api.core.OrderDto;
import com.prumi.web.api.core.OrderItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component

public class OrderServiceIntegration {
    @Qualifier("Order")
    @Autowired
    private WebClient coreServiceWebClient;

    public List<OrderItemDto> getOrder(){
        return coreServiceWebClient.get()
                .uri("/api/v1/orders/statOrder")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OrderItemDto>>() {
                })
                .block();
    }

    public void clearOrderList(){
        coreServiceWebClient.get()
                .uri("/api/v1/orders/statOrder/clear")
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
}
