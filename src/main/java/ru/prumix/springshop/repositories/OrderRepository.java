package ru.prumix.springshop.repositories;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.prumix.springshop.dto.ProductDto;
import ru.prumix.springshop.entities.Order;


import java.util.Map;

@Repository
@Data
@RequiredArgsConstructor
public class OrderRepository {
    private final Map<Long,Order> orders;

    public Map<Long,Order> findAll() {
        return orders;
    }

    public Order findByIndex(Long index){
        return orders.get(index);

    }


    public Long getMaxIndex(){
        return orders.keySet().stream()
                .max(Long::compareTo)
                .orElse(0L);
    }


    public void addOrder(ProductDto productDto) {
        orders.put(getMaxIndex() + 1,new Order(productDto, 1));
        System.out.println(orders.keySet().stream()
                .max(Long::compareTo)
                .orElse(0L));
    }

    public void updateOrder(Order order, Long index){
        orders.put(index,order);
    }
}
