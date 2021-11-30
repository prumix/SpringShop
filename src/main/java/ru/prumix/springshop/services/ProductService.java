package ru.prumix.springshop.services;


import org.springframework.stereotype.Service;
import ru.prumix.springshop.entities.Product;
import ru.prumix.springshop.exceptions.ResourceNotFoundException;
import ru.prumix.springshop.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
   private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void changeCost(Long id, Integer delta) {
       Product product = repository.findById(id)
               .orElseThrow(() ->  new ResourceNotFoundException("Max or Min cost: " + delta));
        product.setCost(product.getCost() + delta);
        repository.save(product);
    }

    public List<Product> findAllByCostBetween(Integer min, Integer max) {
        return repository.findProductsByCostBetween(min, max);
    }

    public List<Product> findProductsByCostAfter(Integer min) {
        return repository.findProductsByCostAfter(min);
    }

    public List<Product> findProductsByCostBefore(Integer max) {
        return repository.findProductsByCostBefore(max);
    }
}
