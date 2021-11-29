package ru.prumix.springshop.repositories;

import org.springframework.stereotype.Repository;
import ru.prumix.springshop.model.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ProductRepositoryMEM {
    private List<Product> products;

    @PostConstruct
    public void init(){
        products = new ArrayList<>(/*List.of(
                new Product(1L, "Apples", 50),
                new Product(2L,"Oranges", 20),
                new Product(3L,"Bananas", 15),
                new Product(4L,"Potatil", 45),
                new Product(5L,"Marshmallow", 90)
        )*/);
    }

    public List<Product> getAllProducts(){
        return Collections.unmodifiableList(products);
    }

    public Product findById(Long id){
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->new RuntimeException("Not find product by id:" + id));
    }

    public void deleteById(Long id){
        products.removeIf(s->s.getId().equals(id));
    }


}
