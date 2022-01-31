package com.prumi.web.core.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.prumi.web.api.dto.CartAndOrderDetails;
import com.prumi.web.api.dto.CartDto;
import com.prumi.web.core.converters.OrderConverter;
import com.prumi.web.api.dto.OrderDetailsDto;
import com.prumi.web.core.dto.OrderDto;
import com.prumi.web.core.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username,
                            @RequestBody CartAndOrderDetails cartAndOrderDetails) {
        CartDto cartDto = cartAndOrderDetails.getCartDto();
        OrderDetailsDto orderDetailsDto = cartAndOrderDetails.getOrderDetailsDto();
        orderService.createOrder(username, orderDetailsDto, cartDto);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username) {
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}
