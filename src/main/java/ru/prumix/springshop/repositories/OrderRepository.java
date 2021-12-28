package ru.prumix.springshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.prumix.springshop.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select max(o.id) from Order o where o.userId =:userId")
    Long findOrderIdByUserId(@Param("userId") Long userId);
}