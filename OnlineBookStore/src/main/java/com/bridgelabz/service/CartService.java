package com.bridgelabz.service;

import com.bridgelabz.exception.BookException;
import com.bridgelabz.exception.CartException;
import com.bridgelabz.model.Book;
import com.bridgelabz.model.Cart;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.repository.BookStoreRepository;
import com.bridgelabz.repository.CartRepository;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.utility.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    public BookStoreRepository bookstoreRepository;

    @Override
    public String addToCart(String token, Long bookId) throws BookException {
        Long userId=JwtGenerator.decodeJWT(token);
        Book book = bookstoreRepository.findById(bookId)
                .orElseThrow(() -> new BookException("book does not exist", BookException.ExceptionType.BOOKS_NOT_AVAILABLE));
        Cart cartModel = new Cart(book);
        Optional<UserModel> user = userRepository.findById(userId);
        cartModel.setUserDetails(user.get());
        cartRepository.save(cartModel);
        return  "book added to cart successfully";
    }

    @Override
    public List<Cart> removeItem(Long bookId, String token) {
        Long userId=JwtGenerator.decodeJWT(token);
        List<Cart> items = cartRepository.findByUserId(userId);
        List<Cart> selectedItems = items.stream().filter(cartItem -> cartItem.getBookId().equals(bookId))
                .collect(Collectors.toList());
        for (Cart book:selectedItems) {
            cartRepository.delete(book);
        }
        return cartRepository.findByUserId(userId);
    }

    @Override
    public  List<Cart> getAllItemFromCart(String token) throws CartException {
        Long userId=JwtGenerator.decodeJWT(token);
        List<Cart> items = cartRepository.findByUserId(userId);
        if (items.isEmpty())
            throw new CartException("cart is empty", CartException.ExceptionType.EMPTY_CART);
        return items;
    }

    @Override
    public String deleteAll() {
        cartRepository.deleteAll();
        return "Items Removed Successfully";
    }

    @Override
    public List<Cart> addMoreItems(Long bookId,String token) {
        Long userId=JwtGenerator.decodeJWT(token);
        List<Cart> items = cartRepository.findByUserId(userId);
        List<Cart> selectedItems = items.stream().filter(cartItem -> cartItem.getBookId().equals(bookId))
                .collect(Collectors.toList());
        for (Cart book:selectedItems) {
            book.setQuantity(book.getQuantity()+1);
            cartRepository.save(book);
        }
        return cartRepository.findByUserId(userId);
    }
    @Override
    public List<Cart> subtractItem(Long bookId, String token) {
        Long userId=JwtGenerator.decodeJWT(token);
        List<Cart> items = cartRepository.findByUserId(userId);
        List<Cart> selectedItems = items.stream().filter(cartItem -> cartItem.getBookId().equals(bookId))
                .collect(Collectors.toList());
        if(selectedItems.get(0).getQuantity()==1)
        {
            cartRepository.delete(selectedItems.get(0));
            return cartRepository.findByUserId(userId);
        }
        for (Cart book:selectedItems) {
            book.setQuantity(book.getQuantity()-1);
            cartRepository.save(book);
        }
        return cartRepository.findByUserId(userId);
    }
}