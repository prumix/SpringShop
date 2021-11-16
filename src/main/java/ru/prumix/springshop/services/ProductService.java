package ru.prumix.springshop.services;

import org.springframework.stereotype.Service;
import ru.prumix.springshop.model.Product;
import ru.prumix.springshop.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll(){
        return productRepository.getAllProducts();
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    public void changeCost(Long id, Integer delta) {
        Product product = productRepository.findById(id);
        product.setCost(product.getCost() + delta);
    }
}
