package ru.prumix.springshop.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.prumix.springshop.entities.Product;

public class ProductSpecifications {
    public static Specification<Product> priceGreaterOrEqualsThan(Integer minPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> priceLessThanOrEqualsThan(Integer maxPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }

}
