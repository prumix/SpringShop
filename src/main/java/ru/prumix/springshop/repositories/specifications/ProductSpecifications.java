package ru.prumix.springshop.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.prumix.springshop.entities.Product;

public class ProductSpecifications {
    public static Specification<Product> costGreaterOrEqualsThan(Integer minCost) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), minCost);
    }

    public static Specification<Product> costLessThanOrEqualsThan(Integer maxCost) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("cost"), maxCost);
    }

}
