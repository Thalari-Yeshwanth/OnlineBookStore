package com.bridgelabz.service;

import com.bridgelabz.model.*;
import com.bridgelabz.repository.*;
import com.bridgelabz.utility.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    public CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Long placeOrder(String token) {
        Long orderId= generateOrderId();
        Long userId=JwtGenerator.decodeJWT(token);
        List<Cart> cart = cartRepository.findByUserId(userId);
        double totalPrice= cart.stream().mapToDouble(book -> book.getPrice() * book.getQuantity()).sum();
        Optional<Customer> customer = customerRepository.findById(userId);
        Order order=new Order(orderId, userId, cart, totalPrice, customer.get());
        orderRepository.save(order);
        return orderId;
    }
    @Override
    public  Order getOrderSummary(String token)  {
        Long userId= JwtGenerator.decodeJWT(token);
        Optional<Order> userOrders = orderRepository.findByUserId(userId);
        return userOrders.get();
    }

    public Long generateOrderId() {
        boolean isUnique =false;
        Long orderId= Long.valueOf(0);
        while (!isUnique){
            orderId=(long) Math.floor(100000+Math.random()*999999);
            Optional<Order> byId = orderRepository.findById(orderId);
            if(!byId.isPresent()) {
                isUnique = true;
            }
        }
        return orderId;
    }
}
