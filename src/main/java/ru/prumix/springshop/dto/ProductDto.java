package ru.prumix.springshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.prumix.springshop.entities.Product;
import ru.prumix.springshop.entities.ProductCategory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private String category;
    private Integer price;

    public ProductDto(Product product, ProductCategory productCategory) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.category = productCategory.getTitle();
        this.price = product.getPrice();
    }
}
