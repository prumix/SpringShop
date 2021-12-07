package ru.prumix.springshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.prumix.springshop.entities.Product;
import ru.prumix.springshop.exceptions.ResourceNotFoundException;
import ru.prumix.springshop.services.ProductService;

import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;

    public ProductController() {
    }

    @GetMapping("/products")
    public Page<Product> getProductsList(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_cost", defaultValue = "0") Integer minCost,
            @RequestParam(name = "max_cost", required = false) Integer maxCost
    ) {
        if (page<1){
            page = 1;
        }
        return productService.find(minCost,maxCost,page);
    }


    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found, id: " + id)
        );
    }

    @GetMapping("/products/delete/{id}")
    public List<Product> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return productService.findAllProducts();
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
