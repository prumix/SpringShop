package ru.prumix.springshop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.prumix.springshop.dao.ProductDao;
import ru.prumix.springshop.dao.ProductDaoImpl;
import ru.prumix.springshop.model.Product;
import ru.prumix.springshop.utils.SessionFactoryUtils;

import java.util.List;

@Service
public class ProductService {
    private ProductDao productDao;
    private SessionFactoryUtils sessionFactoryUtils;


    public ProductService() {
        this.sessionFactoryUtils = new SessionFactoryUtils();
        sessionFactoryUtils.init();
        this.productDao = new ProductDaoImpl(sessionFactoryUtils);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public Product findById(Long id) {
        return productDao.findById(id);
    }

    public void deleteById(Long id) {
        productDao.deleteById(id);
    }

    public void changeCost(Long id, Integer delta) {
        Product product = productDao.findById(id);
        product.setCost(product.getCost() + delta);
        productDao.saveOrUpdate(product);
    }
}
