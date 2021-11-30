package ru.prumix.springshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.prumix.springshop.entities.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findProductsByCostBetween(Integer min, Integer max);

    List<Product> findProductsByCostAfter(Integer min);

    List<Product> findProductsByCostBefore(Integer max);
}
