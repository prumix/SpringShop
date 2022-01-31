package com.prumi.web.core.converters;

import com.prumi.web.api.dto.ProductDto;
import com.prumi.web.core.entities.Product;
import com.prumi.web.core.entities.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(),new ProductCategory(productDto.getCategory()),  productDto.getPrice());
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getProductCategory().getTitle(), product.getPrice());
    }
}
