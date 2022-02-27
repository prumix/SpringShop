package com.prumi.web.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusOrder {
    CREATED("Создан"),
    PAID("Оплачен"),
    CANCELED("Отменен");

    private String tittle;
}
