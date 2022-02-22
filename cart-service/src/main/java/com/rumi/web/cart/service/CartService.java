package com.rumi.web.cart.service;


import com.prumi.web.api.carts.CartDto;
import com.prumi.web.api.carts.CartItemDto;
import com.prumi.web.api.core.ProductDto;
import com.prumi.web.api.exceptions.ResourceNotFoundException;
import com.rumi.web.cart.converters.CartConverter;
import com.rumi.web.cart.integrations.ProductsServiceIntegration;
import com.rumi.web.cart.models.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final ProductsServiceIntegration productsServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;
    private final List<CartItemDto> cartItemDtoList = new CopyOnWriteArrayList<>();

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    public List<CartItemDto> getAllCartItems() {

        return cartItemDtoList;
    }

    public void clearCartItemDtoList() {
        cartItemDtoList.clear();
    }

    public void addToCart(String cartKey, Long productId) {
        ProductDto productDto = productsServiceIntegration.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найдет, id: " + productId));
        execute(cartKey, c -> {
            c.add(productDto);
        });

        CartItemDto cartItemDto = new CartItemDto(0L, productDto.getTitle(), 1, BigDecimal.ZERO, BigDecimal.ZERO);
        if (cartItemDtoList.size() ==0){
            cartItemDtoList.add(cartItemDto);
        }
        else {
            for (Iterator<CartItemDto> iterator = cartItemDtoList.listIterator(); iterator.hasNext();) {
                if (cartItemDto.getProductTitle().equals(iterator.next().getProductTitle())) {
                    iterator.next().setQuantity(iterator.next().getQuantity() + 1);

                } else {
                    cartItemDtoList.add(cartItemDto);
                }
            }
        }
    }


    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, c -> c.remove(productId));
    }

    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, c -> c.decrement(productId));
    }

    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}
