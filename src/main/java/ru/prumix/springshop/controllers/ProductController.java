package ru.prumix.springshop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.prumix.springshop.entities.Product;
import ru.prumix.springshop.exceptions.ResourceNotFoundException;
import ru.prumix.springshop.services.ProductService;

import java.util.List;

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

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Product not found, id: " + id));
    }

    @GetMapping("/products/delete/{id}")
    public void deleteById(@PathVariable Long id){
        productService.deleteById(id);
    }

    @GetMapping("/products/change_cost")
    public void changeCost(@RequestParam Long id, @RequestParam Integer delta) {
        productService.changeCost(id, delta);
    }

    @GetMapping("/products/cost_between")
    public List<Product> findProductsCostBetween(@RequestParam(defaultValue = "0") Integer min,
                                                    @RequestParam(defaultValue = "100") Integer max) {
        return productService.findAllByCostBetween(min, max);
    }

    @GetMapping("/products/cost_after")
    public List<Product> findProductsByCostAfter(@RequestParam(defaultValue = "0") Integer min) {
        return productService.findProductsByCostAfter(min);
    }

    @GetMapping("/products/cost_before")
    public List<Product> findProductsByCostBefore(@RequestParam(defaultValue = "100") Integer max) {
        return productService.findProductsByCostBefore(max);
    }
}
