package ru.prumix.springshop.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prumix.springshop.dto.ProductDto;
import ru.prumix.springshop.entities.Product;
import ru.prumix.springshop.entities.ProductCategory;
import ru.prumix.springshop.exceptions.ResourceNotFoundException;
import ru.prumix.springshop.repositories.ProductsRepository;
import ru.prumix.springshop.repositories.specifications.ProductsSpecifications;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;

    public Page<Product> findAll(Integer minPrice, Integer maxPrice, String partTitle, String partCat, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (partTitle != null) {
            spec = spec.and(ProductsSpecifications.titleLike(partTitle));
        }
        if (partCat != null){
            spec = spec.and(ProductsSpecifications.categoryLike(partCat));
        }

        return productsRepository.findAll(spec, PageRequest.of(page - 1, 50));
    }


    public Optional<Product> findById(Long id) {
        return productsRepository.findById(id);
    }

    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }

    public Product save(Product product) {
        return productsRepository.save(product);
    }

    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productsRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить продукта, не надйен в базе, id: " + productDto.getId()));
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        return product;
    }



    public static final Function<Product, ru.prumix.springshop.saop.product.Product> functionEntityToSoap = se -> {
        ru.prumix.springshop.saop.product.Product s = new ru.prumix.springshop.saop.product.Product();
        s.setId(se.getId());
        s.setTitle(se.getTitle());
        s.setPrice(se.getPrice());
        s.setCategory(se.getProductCategory().getTitle());
        return s;
    };

    public List<ru.prumix.springshop.saop.product.Product> getAllProducts() {
        return productsRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public ru.prumix.springshop.saop.product.Product getById(Long id) {
        return productsRepository.findById(id).map(functionEntityToSoap).get();
    }
}
