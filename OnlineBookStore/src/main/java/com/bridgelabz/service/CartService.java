package com.bridgelabz.service;

import com.bridgelabz.exception.BookException;
import com.bridgelabz.exception.CartException;
import com.bridgelabz.model.Book;
import com.bridgelabz.model.Cart;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.repository.BookStoreRepository;
import com.bridgelabz.repository.CartRepository;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.response.Response;
import com.bridgelabz.utility.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private BookStoreRepository bookstoreRepository;


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
        Long id = JwtGenerator.decodeJWT(token);
        List<Cart> items = cartRepository.findByUserId(id).stream().filter(c -> !c.isInWishList()).collect(Collectors.toList());
        if (items.isEmpty())
            return new ArrayList<>();
        return items;

    }

    @Override
    public String deleteAll(String token) {
        Long userId = JwtGenerator.decodeJWT(token);
        cartRepository.deleteByUserId(userId);
        return "Items removed Successfully";
    }

    @Override
    public List<Cart> addMoreItems(Long bookId,String token) {
        Long userId=JwtGenerator.decodeJWT(token);
        List<Cart> items = cartRepository.findByUserId(userId);
        List<Cart> selectedItems = items.stream().filter(cartItem -> cartItem.getBookId().equals(bookId))
                .collect(Collectors.toList());
        Optional<Book> book1 = bookstoreRepository.findById(bookId);
        if(book1.get().getQuantity()<selectedItems.get(0).getQuantity()){
            return cartRepository.findByUserId(userId);
        }
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

    @Override
    public Response addToWishList(Long bookId, String token) throws BookException {
        long id = JwtGenerator.decodeJWT(token);
        Cart cartData = cartRepository.findByUserIdAndBookId(id, bookId);

        Long bookid = cartRepository.findduplicatebookId(bookId);
        if(bookid!=bookId) {
            if (cartData != null && cartData.isInWishList()) {
                return new Response(HttpStatus.OK.value(), "Book already present in wishlist");
            } else if (cartData != null && !cartData.isInWishList()) {
                return new Response(HttpStatus.OK.value(), "Book already added to Cart");
            } else {
                Book book = bookstoreRepository.findById(bookId)
                        .orElseThrow(() -> new BookException("book does not exist", BookException.ExceptionType.BOOKS_NOT_AVAILABLE));
                Cart cartModel = new Cart(book);
                Optional<UserModel> user = userRepository.findById(id);
                cartModel.setUserDetails(user.get());
                cartModel.setInWishList(true);
                cartRepository.save(cartModel);
                return new Response(HttpStatus.OK.value(), "Book added to WishList");
            }
        }
        throw new BookException("Book already present in wishlist", BookException.ExceptionType.ALREADY_IN_WISHLIST);

    }


    @Override
    public List<Cart> deleteFromWishlist(Long bookId, String token) {
        Long userId = JwtGenerator.decodeJWT(token);
        List<Cart> items = cartRepository.findByUserId(userId).stream().filter(Cart::isInWishList).collect(Collectors.toList());
        List<Cart> selectedItems = items.stream().filter(cartItem -> cartItem.getBookId().equals(bookId))
                .collect(Collectors.toList());
        for (Cart book : selectedItems) {
            cartRepository.delete(book);
        }
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Response addFromWishlistToCart(Long bookId, String token) {
        long id = JwtGenerator.decodeJWT(token);
        Cart cartModel = cartRepository.findByUserIdAndBookId(id, bookId);
        if(cartModel.isInWishList()){
            cartModel.setInWishList(false);
            cartRepository.save(cartModel);
            return new Response(HttpStatus.OK.value(), "Successfully added book to cart from wishlist");
        }
        return new Response(HttpStatus.OK.value(), "Already present in cart, ready to checkout");
    }

    @Override
    public List<Cart> getAllItemFromWishList(String token) throws BookException {
        Long id = JwtGenerator.decodeJWT(token);
        List<Cart> items = cartRepository.findByUserId(id).stream().filter(Cart::isInWishList).collect(Collectors.toList());
        if (items.isEmpty())
            return new ArrayList<>();
        return items;
    }


}