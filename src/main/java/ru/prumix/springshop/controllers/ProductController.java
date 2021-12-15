package ru.prumix.springshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.prumix.springshop.converters.ProductConverter;
import ru.prumix.springshop.dto.ProductDto;
import ru.prumix.springshop.entities.Order;
import ru.prumix.springshop.entities.Product;
import ru.prumix.springshop.exceptions.ResourceNotFoundException;
import ru.prumix.springshop.services.OrderService;
import ru.prumix.springshop.services.ProductService;
import ru.prumix.springshop.validators.ProductValidator;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productsService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;
    private final OrderService orderService;

    @GetMapping
    public Page<ProductDto> getProductsList(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_cost", defaultValue = "0") Integer minCost,
            @RequestParam(name = "max_cost", required = false) Integer maxCost,
            @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if (page < 1) {
            page = 1;
        }
        return productsService.findAll(minCost, maxCost, titlePart, page).map(productConverter::entityToDto);
    }


    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        Product product = productsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        return productConverter.entityToDto(product);
    }

    @PostMapping
    public ProductDto saveNewProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        product = productsService.save(product);
        return productConverter.entityToDto(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productsService.deleteById(id);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productsService.update(productDto);
        return productConverter.entityToDto(product);
    }

    @PostMapping("/order/changeCount")
    public void changeCount(@RequestParam Integer delta, @RequestParam Long index) {
        Order order = orderService.findByIndex(index);
        Integer startCost = productsService.findById(order.getProductDto().getId()).get().getCost();
        order.setCount(order.getCount() + delta);
        order.getProductDto().setCost(startCost*order.getCount());
        orderService.updateOrder(order,index);


    }

    @GetMapping("/order")
    public Map<Long, Order> getOrder() {
        return orderService.findAll();
    }

    @PostMapping("/order/add")
    public void addToOrder(@RequestParam Long id, @RequestParam String title, @RequestParam Integer cost) {
        ProductDto productDto = new ProductDto(id, title, cost);
        orderService.addOrder(productDto);


    }
}
