package ru.prumix.springshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.prumix.springshop.converters.ProductConverter;
import ru.prumix.springshop.dto.ProductDto;
import ru.prumix.springshop.entities.Product;
import ru.prumix.springshop.exceptions.ResourceNotFoundException;
import ru.prumix.springshop.services.ProductService;
import ru.prumix.springshop.validators.ProductValidator;

import javax.annotation.PostConstruct;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productsService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;
    private final Map<String, Integer> orderMap;

    @PostConstruct
    public void init() {
        orderMap.put("product3", 1);
        orderMap.put("product4", 1);
    }

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
    public void changeCount(@RequestParam String title, @RequestParam Integer delta) {
        orderMap.put(title, orderMap.get(title) + delta);
    }

    @GetMapping("/order")
    public Map<String, Integer> getOrder() {
        return orderMap;
    }

    @PostMapping("/order/add")
    public void addToOrder(@RequestParam Long id) {
        orderMap.put(productsService.findByTitle(id),1);

    }
}
