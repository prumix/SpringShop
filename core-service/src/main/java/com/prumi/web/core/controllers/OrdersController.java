package com.prumi.web.core.controllers;


import com.prumi.web.api.carts.CartItemDto;
import com.prumi.web.api.core.OrderDetailsDto;
import com.prumi.web.api.core.OrderDto;
import com.prumi.web.api.core.OrderItemDto;
import com.prumi.web.api.exceptions.AppError;
import com.prumi.web.api.exceptions.ResourceNotFoundException;
import com.prumi.web.core.converters.OrderConverter;
import com.prumi.web.core.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name="Заказы", description = "Методы работы с заказами")
public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @Operation(
            summary = "Запрос на создание заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto) {
        orderDetailsDto.setCountryCode("RU");
        orderService.createOrder(username, orderDetailsDto);
    }

    @Operation(
            summary = "Запрос на получение списка заказов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = OrderDto.class))
                    )
            }
    )
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

    @Operation(
            summary = "Запрос на получение заказа с указанным id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Неуспешный ответ", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderConverter.entityToDto(orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("ORDER 404")));
    }
}
