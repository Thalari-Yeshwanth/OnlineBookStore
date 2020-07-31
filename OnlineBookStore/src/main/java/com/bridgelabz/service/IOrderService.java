package com.bridgelabz.service;

import com.bridgelabz.exception.UserException;
import com.bridgelabz.model.Order;



public interface IOrderService {

    Long placeOrder(String token) throws UserException;

    Order getOrderSummary(String token) throws UserException;
}
