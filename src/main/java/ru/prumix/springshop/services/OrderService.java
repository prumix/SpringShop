package ru.prumix.springshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.prumix.springshop.entities.Order;
import ru.prumix.springshop.repositories.OrderRepository;
import ru.prumix.springshop.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private Order order;

    public void addOrderByUserName(String userName, int totalPrice){
        order = new Order();
        order.setUserId(userRepository.findUserIdByUsername(userName));
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
    }
}
