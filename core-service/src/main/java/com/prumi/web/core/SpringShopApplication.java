package com.prumi.web.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringShopApplication {

    // Домашнее задание:
    // 1. Разобраться с кодом
    // 2. Добавить микросервис рекомендаций:
    // - 5 наиболее покупаемых продуктов за месяц
    // - 5 наиболее складываемых в корзину продуктов за день
    // - Вывести на главной странице эти рекомендации в виде текста

    public static void main(String[] args) {
        SpringApplication.run(SpringShopApplication.class, args);
    }

}
