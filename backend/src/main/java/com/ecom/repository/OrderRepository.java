package com.ecom.repository;

import com.ecom.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByBuyerEmailOrderByOrderDateDesc(String buyerEmail);

    Optional<Order> findByIdAndBuyerEmail(Long id, String buyerEmail);
}
