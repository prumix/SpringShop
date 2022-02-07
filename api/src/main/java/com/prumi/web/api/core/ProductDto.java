package com.prumi.web.api.core;

public class ProductDto {
    private Long id;
    private String title;
    private String category;
    private Integer price;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ProductDto() {
    }

    public ProductDto(Long id, String title, String category, Integer price) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
    }
}
