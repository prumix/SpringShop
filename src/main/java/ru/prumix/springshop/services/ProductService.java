package ru.prumix.springshop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.prumix.springshop.entities.Product;
import ru.prumix.springshop.exceptions.ResourceNotFoundException;
import ru.prumix.springshop.repositories.ProductRepository;
import ru.prumix.springshop.repositories.specifications.ProductSpecifications;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> find(Integer minCost, Integer maxCost, Integer page){
        Specification<Product> spec =Specification.where(null);
        if (minCost != null){
            spec = spec.and(ProductSpecifications.costGreaterOrEqualsThan(minCost));
        }
        if (maxCost !=null){
            spec = spec.and(ProductSpecifications.costLessThanOrEqualsThan(maxCost));
        }
        return productRepository.findAll(spec, PageRequest.of(page-1, 5));
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

}
