package ru.prumix.springshop.converters;

import org.springframework.stereotype.Component;
import ru.prumix.springshop.dto.ProductDto;
import ru.prumix.springshop.entities.Product;

@Component
public class ProductConverter {
    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice());
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }
}
