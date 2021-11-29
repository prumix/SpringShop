package ru.prumix.springshop.dao;

import ru.prumix.springshop.model.Product;
import java.util.List;


public interface ProductDao {
    Product findById(Long id);
    List<Product> findAll();
    void deleteById(Long id);
    Product saveOrUpdate(Product product);
}
