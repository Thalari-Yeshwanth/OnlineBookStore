package com.bridgelabz.repository;

import com.bridgelabz.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query(value = "select * from cart where user_id=:userId", nativeQuery = true)
    List<Cart> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "delete  FROM cart where user_id=:userId",nativeQuery = true)
    void deleteByUserId(Long userId);
}
