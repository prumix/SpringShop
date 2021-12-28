package ru.prumix.springshop.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.prumix.springshop.dto.Cart;
import ru.prumix.springshop.entities.Product;
import ru.prumix.springshop.exceptions.ResourceNotFoundException;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productsService;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
    }

    public Cart getCurrentCart() {
        return cart;
    }

    public void addProductByIdToCart(Long productId) {
        if (!getCurrentCart().addProduct(productId)) {
            Product product = productsService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найдет, id: " + productId));
            getCurrentCart().addProduct(product);
        }
    }

    public void clear() {
        getCurrentCart().clear();
    }
}
