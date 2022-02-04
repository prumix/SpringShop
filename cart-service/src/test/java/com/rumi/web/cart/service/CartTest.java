package com.rumi.web.cart.service;

import com.prumi.web.api.dto.OrderItemDto;
import com.prumi.web.api.dto.ProductDto;
import com.rumi.web.cart.dto.Cart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartTest {

    @Autowired
    private CartService cartService;

    @BeforeEach
    public void initCart() {
        cartService.clearCart("test_cart");
    }

    @Test
    public void addToCartTest() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setTitle("product1");
        productDto.setCategory("meat");
        productDto.setPrice(10);

        cartService.addToCart("test_cart", productDto);
        cartService.addToCart("test_cart", productDto);
        cartService.addToCart("test_cart", productDto);

        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().size());
    }

    @Test
    public void clearCartTest() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setTitle("product1");
        productDto.setCategory("meat");
        productDto.setPrice(10);

        cartService.addToCart("test_cart", productDto);
        cartService.addToCart("test_cart", productDto);
        cartService.addToCart("test_cart", productDto);

        cartService.clearCart("test_cart");

        Assertions.assertEquals(0, cartService.getCurrentCart("test_cart").getItems().size());
    }

    @Test
    public void removeItemFromCartTest() {
        for (int i = 0; i < 5; i++){
            ProductDto productDto = new ProductDto();
            productDto.setId(1L + i);
            productDto.setTitle("product" + i);
            productDto.setCategory("meat");
            productDto.setPrice(10);

            cartService.addToCart("test_cart", productDto);
        }
        Assertions.assertEquals(5, cartService.getCurrentCart("test_cart").getItems().size());

        cartService.removeItemFromCart("test_cart", 2L);

        Assertions.assertEquals(4,cartService.getCurrentCart("test_cart").getItems().size());
    }

    @Test
    public void updateCart(){
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(1L);
        orderItemDto.setProductTitle("product");
        orderItemDto.setPricePerProduct(1);
        orderItemDto.setQuantity(1);
        orderItemDto.setPrice(10);

        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setTitle("product");
        productDto.setCategory("meat");
        productDto.setPrice(10);

        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        orderItemDtoList.add(orderItemDto);

        Cart cart = new Cart();
        cart.setItems(orderItemDtoList);
        cart.setTotalPrice(20);

        cartService.addToCart("test_cart", productDto);

        cartService.updateCart("test_cart",cart);

        Assertions.assertEquals(20,cartService.getCurrentCart("test_cart").getTotalPrice());
    }

    @Test
    public void mergeTest(){
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(1L);
        orderItemDto.setProductTitle("product");
        orderItemDto.setPricePerProduct(1);
        orderItemDto.setQuantity(1);
        orderItemDto.setPrice(10);

        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        orderItemDtoList.add(orderItemDto);

        Cart guestCart = new Cart();
        guestCart.setItems(orderItemDtoList);
        guestCart.setTotalPrice(20);

        Cart userCart = new Cart();
        cartService.clearCart("test_cart_user");
        cartService.updateCart("test_cart_user",userCart);

        cartService.updateCart("test_cart", guestCart);

        cartService.merge("test_cart_user","test_cart");

        Assertions.assertEquals(0,cartService.getCurrentCart("test_cart").getItems().size());
        Assertions.assertEquals(1,cartService.getCurrentCart("test_cart_user").getItems().size());


    }

}