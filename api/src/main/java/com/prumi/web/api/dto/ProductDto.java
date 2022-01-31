package com.prumi.web.api.dto;

import lombok.Data;


@Data
public class ProductDto {
    private Long id;
    private String title;
    private String category;
    private Integer price;

    public ProductDto() {
    }


    public ProductDto(Long id, String title, String category, Integer price) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
    }
}
