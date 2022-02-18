package com.prumi.web.core.controllers;


import com.prumi.web.api.carts.CartItemDto;
import com.prumi.web.api.core.OrderDetailsDto;
import com.prumi.web.api.core.OrderDto;
import com.prumi.web.api.core.OrderItemDto;
import com.prumi.web.core.converters.OrderConverter;
import com.prumi.web.core.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto) {
        orderService.createOrder(username, orderDetailsDto);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username) {
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/statOrder")
    public List<OrderItemDto> getOrderStatistic() {
        return orderService.getAllOrderItems();
    }

    @GetMapping("/statOrder/clear")
    public boolean clearCartItemDtoList(){
        System.out.println(true);
        orderService.clearOrderItemDto();
        return true;
    }
}
