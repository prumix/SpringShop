package com.prumi.web.recommendations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticItem {
    private String title;
    private String from;
    private int quantity;

    public StatisticItem(String title, String from) {
        this.title = title;
        this.from = from;
    }

    public void changeQuantity(int delta) {
        this.quantity += delta;
    }
}
