package ru.prumix.springshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.prumix.springshop.dto.ProductDto;
import ru.prumix.springshop.entities.Order;
import ru.prumix.springshop.repositories.OrderRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;

    public Map<Long, Order> findAll() {
        return repository.findAll();
    }

    public Order findByIndex(Long index) {
        return repository.findByIndex(index);
    }

    public void addOrder(ProductDto productDto) {
        repository.addOrder(productDto);
    }

    public void updateOrder(Order order, Long index){
       repository.updateOrder(order, index);
    }



}
