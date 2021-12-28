package ru.prumix.springshop.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import ru.prumix.springshop.dto.Cart;
import ru.prumix.springshop.services.OrderItemService;
import ru.prumix.springshop.services.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PostMapping
    public void checkoutCart(@RequestBody String json) throws JsonProcessingException {
        JSONObject orderJson = new JSONObject(json);
        ObjectMapper mapper = new ObjectMapper();
        Cart cart = mapper.readValue(orderJson.get("cart").toString(), Cart.class);
        String userName = orderJson.getString("userName");
        int totalPrice = cart.getTotalPrice();
        orderService.addOrderByUserName(userName, totalPrice);
        orderItemService.addOrderItemByUserName(userName,cart);
    }
}
