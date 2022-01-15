package ru.prumix.springshop.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.prumix.springshop.entities.Product;

public class ProductsSpecifications {
    public static Specification<Product> priceGreaterOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> priceLessThanOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }


    public static Specification<Product> categoryLike(String categoryPart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("productCategory").get("title"), String.format("%%%s%%", categoryPart));
    }

}
