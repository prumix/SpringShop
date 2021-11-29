package ru.prumix.springshop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.prumix.springshop.model.Product;
import ru.prumix.springshop.services.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getProducts(){
        return productService.findAll();
    }

    @GetMapping("/products/delete/{id}")
    public void deleteById(@PathVariable Long id){
        productService.deleteById(id);
    }

    @GetMapping("/products/change_cost")
    public void changeCost(@RequestParam Long id, @RequestParam Integer delta) {
        productService.changeCost(id, delta);
    }
}
