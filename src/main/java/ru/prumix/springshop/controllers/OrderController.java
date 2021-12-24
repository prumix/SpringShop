package ru.prumix.springshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.prumix.springshop.bean.Order;
import ru.prumix.springshop.dto.ProductDto;
import ru.prumix.springshop.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final ProductService productService;
    private final Order order;

    @PostMapping("/changeCount")
    public void changeCount(@RequestParam Integer delta, @RequestParam Long id, @RequestParam String username) {
        Order oldOrder = order.findByProductIdForUsername(id, username);
        Order orderFrom = order.findByProductIdForUsername(id, username);
        Integer startCost = productService.findById(orderFrom.getProductDto().getId()).get().getCost();
        orderFrom.setCount(orderFrom.getCount() + delta);
        orderFrom.getProductDto().setCost(startCost*orderFrom.getCount());
        order.updateOrder(orderFrom,oldOrder);


    }

    @GetMapping
    public List<Order> getOrder(String username) {
        return order.findByUser(username);
    }

    @PostMapping("/add")
    public void addToOrder(@RequestParam Long id,
                           @RequestParam String username,
                           @RequestParam String title,
                           @RequestParam Integer cost) {
        ProductDto productDto = new ProductDto(id, title, cost);
        int defaultCost = productDto.getCost();
        order.addOrder(username,productDto,defaultCost);
    }
}
