package com.bridgelabz.repository;

import com.bridgelabz.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query(value = "SELECT * FROM onlinebookstore.order where user=:userId",nativeQuery = true)
    Optional<Order> findByUserId(Long userId);
}

