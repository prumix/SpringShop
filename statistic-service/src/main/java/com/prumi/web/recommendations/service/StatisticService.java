package com.prumi.web.recommendations.service;


import com.prumi.web.api.carts.CartDto;
import com.prumi.web.api.carts.CartItemDto;
import com.prumi.web.api.core.OrderItemDto;
import com.prumi.web.api.statistic.StatisticDto;
import com.prumi.web.recommendations.integrations.CartServiceIntegration;
import com.prumi.web.recommendations.integrations.OrderServiceIntegration;
import com.prumi.web.recommendations.model.Statistic;
import com.prumi.web.recommendations.model.StatisticItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticService {
    private final CartServiceIntegration cartServiceIntegration;
    private final OrderServiceIntegration orderServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;


    public Statistic getCurrentStatistic(String statisticKey) {
        if (!redisTemplate.hasKey(statisticKey)) {
            redisTemplate.opsForValue().set(statisticKey, new Statistic());
        }
        Statistic statistic = (Statistic)redisTemplate.opsForValue().get(statisticKey);
        log.info(statistic.toString());
        List<StatisticItem> statisticItems = statistic.getItemList().stream()
                .filter(statisticItem -> statisticItem.getFrom().equals(statisticKey))
                .sorted(Comparator.comparing(StatisticItem::getQuantity))
                .limit(5)
                .collect(Collectors.toList());
        Statistic result = new Statistic();
        result.setItemList(statisticItems);
        log.info("result-----" + result);
        return result;
    }

    @Scheduled(cron = "0 0/15 * * * *")  // Every 15 minutes
    public void addToCartStatistic() {
        List<CartItemDto> cartItemDtoList = cartServiceIntegration.getCarts();
            execute("Cart", c -> {
                c.addCart(cartItemDtoList);
                System.out.println(c);
            });
    }
    @Scheduled(cron = "0 0/16 * * * *") // Every 16 minutes
    public void clearCartItemDto(){
        cartServiceIntegration.clearCartList();
    }

    @Scheduled(cron = "0 0 0/1 * * *") // Every 1 hour
    public void addToOrderStatistic() {
        List<OrderItemDto> orderItemDtoList = orderServiceIntegration.getOrder();
        execute("Order", c -> {
            c.addOrder(orderItemDtoList);
            System.out.println(c);
        });
    }

    @Scheduled(cron = "0 1 0/1 * * *") // Every 1.1 hour
    public void clearOrderItemDto(){
        orderServiceIntegration.clearOrderList();
    }
    @Scheduled(cron = "0 59 23 * * *")
    public void clearCartStatistic() {
        execute("Cart", Statistic::clear);
    }

    @Scheduled(cron = "0 0 0 L * *") // last day of the month at midnight
    public void clearOrderStatistic() {
        execute("Order", Statistic::clear);
    }

    public void updateCart(String statisticKey, Statistic statistic) {
        redisTemplate.opsForValue().set(statisticKey, statistic);
    }

    private void execute(String statisticKey, Consumer<Statistic> action) {
        Statistic statistic = getCurrentStatistic(statisticKey);
        action.accept(statistic);
        redisTemplate.opsForValue().set(statisticKey, statistic);
    }


}
