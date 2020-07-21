package com.bridgelabz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity


public class Order {

    @Id
    public Long orderId;
    public Long user;
    public Double totalPrice;
    public LocalDate orderPlacedDate;

    @OneToMany()
    public List<Cart> cartbooks;

    @JsonIgnore
    @OneToOne()
    @JoinColumn(name = "customer")
    public Customer customer;

    public Order(Long orderId, Long userid, List<Cart> cart, double totalPrice, Customer customer) {
        this.orderId=orderId;
        this.user=userid;
        this.cartbooks=cart;
        this.totalPrice=totalPrice;
        this.orderPlacedDate=LocalDate.now();
        this.customer=customer;
    }
}
