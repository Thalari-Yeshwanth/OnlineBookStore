package com.bridgelabz.service;

import com.bridgelabz.exception.BookException;
import com.bridgelabz.exception.CartException;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.model.Cart;
import com.bridgelabz.response.Response;

import java.util.List;

public interface ICartService {

    String addToCart(String token, Long bookId) throws UserException, BookException;

    List<Cart> removeItem(Long bookId, String token) throws BookException, CartException;

    List<Cart> getAllItemFromCart(String token) throws CartException;

    String deleteAll(String token)throws CartException;;

    List<Cart> subtractItem(Long bookId, String token);

    List<Cart> addMoreItems(Long bookId, String token) throws BookException;

    Response addToWishList(Long bookId, String token) throws BookException;

    List<Cart> deleteFromWishlist(Long bookId, String token);

    Response addFromWishlistToCart(Long bookId, String token);

    List<Cart> getAllItemFromWishList(String token) throws BookException;
}
