package com.ecom.repository;

import com.ecom.entity.CustomerBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<CustomerBasket, String> {
}
