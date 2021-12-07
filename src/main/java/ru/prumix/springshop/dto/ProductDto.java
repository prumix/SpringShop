package ru.prumix.springshop.dto;

import ru.prumix.springshop.entities.Product;

import javax.persistence.*;


public class ProductDto {

    private Long id;
    private String title;
    private int cost;

    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.cost = product.getCost();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
