package ru.prumix.springshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.prumix.springshop.dto.Cart;
import ru.prumix.springshop.dto.OrderItemDto;
import ru.prumix.springshop.entities.OrderItem;
import ru.prumix.springshop.repositories.OrderItemRepository;
import ru.prumix.springshop.repositories.OrderRepository;
import ru.prumix.springshop.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public void addOrderItemByUserName(String userName, Cart cart) {
        Long userId = userRepository.findUserIdByUsername(userName);
        Long orderId = orderRepository.findOrderIdByUserId(userId);
        OrderItem orderItem ;
        for (OrderItemDto o : cart.getItems()) {
            orderItem = new OrderItem();
            orderItem.setUserId(userId);
            orderItem.setOrderId(orderId);
            orderItem.setProductId(o.getProductId());
            orderItem.setQuantity(o.getQuantity());
            orderItem.setPricePerProduct(o.getPricePerProduct());
            orderItem.setPrice(o.getPrice());
            orderItemRepository.save(orderItem);
        }
        // orderItemRepository.save(orderItem);


    }
}
