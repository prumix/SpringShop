package ru.prumix.springshop.controllers;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.prumix.springshop.dto.ProductDto;
import ru.prumix.springshop.entities.Product;
import ru.prumix.springshop.exceptions.ResourceNotFoundException;
import ru.prumix.springshop.services.ProductService;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<ProductDto> getProductsList(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_cost", defaultValue = "0") Integer minCost,
            @RequestParam(name = "max_cost", required = false) Integer maxCost
    ) {
        if (page<1){
            page = 1;
        }
        return productService.find(minCost,maxCost,page).map(ProductDto::new);
    }


    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found, id: " + id)
        );
    }

    @PostMapping
    public Product saveNewProduct(@RequestBody ProductDto productDto){
        Product product = new Product();
        product.setId(null);
        product.setTitle(productDto.getTitle());
        product.setCost(productDto.getCost());
        return productService.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @PutMapping
    public Product updateProduct(@RequestBody ProductDto productDto){
        Optional<Product> product = productService.findById(productDto.getId());
        product.get().setCost(productDto.getCost());
        product.get().setTitle(productDto.getTitle());
        return productService.save(product.get());
    }

}
