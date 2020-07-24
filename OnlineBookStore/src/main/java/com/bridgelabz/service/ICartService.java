package com.bridgelabz.service;

import com.bridgelabz.exception.BookException;
import com.bridgelabz.exception.CartException;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.model.Cart;

import java.util.List;

public interface ICartService {

    String addToCart(String token, Long bookId) throws UserException, BookException;

    List<Cart> removeItem(Long bookId, String token) throws BookException;

    List<Cart> getAllItemFromCart(String token) throws CartException;

    String deleteAll(String token);

    List<Cart> subtractItem(Long bookId, String token);

    List<Cart> addMoreItems(Long bookId, String token) throws BookException;
}
