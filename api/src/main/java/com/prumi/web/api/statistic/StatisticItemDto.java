package com.prumi.web.api.statistic;

public class StatisticItemDto {
    private String title;
    private String from;
    private int quantity;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public StatisticItemDto(String title, String from, int quantity) {
        this.title = title;
        this.from = from;
        this.quantity = quantity;
    }
}
