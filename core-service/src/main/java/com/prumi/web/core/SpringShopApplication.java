package com.prumi.web.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringShopApplication {

    // Домашнее задание:
    // 1. Покрыть код кор-сервиса и карт-сервиса доками сваггера
    // 2. Замените оставшийся RestTemplate на WebClient
    // 3. В конфиге сделайте преобразование в:
    // integrations:
    //  cart-service:
    //    url: http://localhost:5555/cart
    //    connect-timeout: 2000
    //    read-timeout: 10000
    //    write-timeout: 2000
    // В:
    // integrations:
    //  cart-service:
    //    url: http://localhost:5555/cart
    //    timeouts:
    //      read: 2000
    //		write: 2000
    //		connection: 1000

    public static void main(String[] args) {
        SpringApplication.run(SpringShopApplication.class, args);
    }

}
