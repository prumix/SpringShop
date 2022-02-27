package com.prumi.web.core.services;


import com.prumi.web.api.carts.CartDto;
import com.prumi.web.api.core.OrderDetailsDto;
import com.prumi.web.api.core.OrderDto;
import com.prumi.web.api.core.OrderItemDto;
import com.prumi.web.api.exceptions.ResourceNotFoundException;
import com.prumi.web.core.converters.AddressConverter;
import com.prumi.web.core.entities.Order;
import com.prumi.web.core.entities.OrderItem;
import com.prumi.web.core.entities.StatusOrder;
import com.prumi.web.core.integrations.CartServiceIntegration;
import com.prumi.web.core.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final ProductsService productsService;
    private final CartServiceIntegration cartServiceIntegration;
    private final AddressConverter addressConverter;
    private List<OrderItemDto> orderItemDtos = new CopyOnWriteArrayList<>();

    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto) {
        CartDto currentCart = cartServiceIntegration.getUserCart(username);
        Order order = new Order();
        order.setAddress(addressConverter.dtoToString(orderDetailsDto));
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(currentCart.getTotalPrice());
        List<OrderItem> items = currentCart.getItems().stream()
                .map(o -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(o.getQuantity());
                    item.setPricePerProduct(o.getPricePerProduct());
                    item.setPrice(o.getPrice());
                    item.setProduct(productsService.findById(o.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        order.setStatusOrder(StatusOrder.CREATED);
        ordersRepository.save(order);

        orderItemDtos = currentCart.getItems().stream()
                .map(o -> {
                    OrderItemDto orderItemDto = new OrderItemDto();
                    orderItemDto.setProductTitle(o.getProductTitle());
                    orderItemDto.setQuantity(o.getQuantity());
                    return orderItemDto;
                }).collect(Collectors.toList());
    }

    public List<Order> findOrdersByUsername(String username) {
        return ordersRepository.findAllByUsername(username);
    }


    public List<OrderItemDto> getAllOrderItems() {
        return orderItemDtos;
    }

    public void clearOrderItemDto(){
        orderItemDtos.clear();
    }

    public Optional<Order> findById(Long id) {
        return ordersRepository.findById(id);
    }
}
