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

import java.util.Map;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productsService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;


    @GetMapping
    public Page<ProductDto> getProductsList(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", defaultValue = "0") Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if (page < 1) {
            page = 1;
        }
        return productsService.findAll(minPrice, maxPrice, titlePart, page).map(productConverter::entityToDto);
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
}
