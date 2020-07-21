package com.bridgelabz.repository;

import com.bridgelabz.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query(value = "select * from cart where user_id=:userId", nativeQuery = true)
    List<Cart> findByUserId(Long userId);
}
