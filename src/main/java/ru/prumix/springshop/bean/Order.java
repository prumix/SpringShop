package ru.prumix.springshop.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.prumix.springshop.dto.ProductDto;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private ProductDto productDto;
    private Integer count;
    private String username;
    private List<Order> orders;

    public Order(String username, ProductDto productDto, Integer count) {
        this.username = username;
        this.productDto = productDto;
        this.count = count;
    }

    @PostConstruct
    public void init() {
        orders = new ArrayList<>();
    }

    public List<Order> findByUser(String username) {
        return orders.stream()
                .filter(order -> order.getUsername().equalsIgnoreCase(username))
                .collect(Collectors.toList());
    }

    public Order findByProductIdForUsername(Long id, String username) {
        return orders.stream()
                .filter(order -> order.getUsername().equals(username))
                .filter(order -> order.getProductDto().getId().equals(id))
                .findFirst()
                .get();
    }


    public void addOrder(String username, ProductDto productDto, int defaultCost) {
        Order newOrder = new Order(username, productDto, 1);
        if (orders.isEmpty()){
            orders.add(newOrder);
        }else {
            for (Order order: orders){
                if (order.getUsername().equals(username) && order.getProductDto().getId().equals(productDto.getId())){
                    order.setCount(order.getCount()+1);
                    order.getProductDto().setCost(order.getCount()*defaultCost);
                }
            }
        }
    }


    public void updateOrder(Order newOrder, Order oldOrder) {
        orders.remove(oldOrder);
        orders.add(newOrder);
    }

    @Override
    public String toString() {
        return "Order{" +
                "productDto=" + productDto +
                ", count=" + count +
                ", username='" + username + '\'' +
                '}';
    }
}
