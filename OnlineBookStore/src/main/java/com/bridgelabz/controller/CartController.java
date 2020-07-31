package com.bridgelabz.controller;

import com.bridgelabz.exception.BookException;
import com.bridgelabz.exception.CartException;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.model.Cart;
import com.bridgelabz.response.Response;
import com.bridgelabz.service.ICartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CartController {

    @Autowired
    public ICartService cartService;

    @ApiOperation(value = "For adding the book to cart")
    @PostMapping("/book")
    public ResponseEntity<Response> addToCart(@RequestParam Long bookId, @RequestHeader String token) throws UserException, BookException {
        String responseMessage = cartService.addToCart(token, bookId);
        return new ResponseEntity<>(new Response(200,responseMessage), HttpStatus.OK);

    }

    @ApiOperation(value = "For removing entire Cart")
    @DeleteMapping("/book")
    public ResponseEntity<Response> removeFromCart(@RequestParam Long bookId, @RequestHeader String token) throws BookException, CartException {
        List<Cart> userCart = cartService.removeItem(bookId,token);
        return new ResponseEntity<>(new Response(200,"Successfully removed book from cart", userCart), HttpStatus.OK);
    }

    @ApiOperation(value = "For incrementing book quantity")
    @PostMapping("/moreitems")
    public ResponseEntity<Response> addMoreItems(@RequestParam Long bookId, @RequestHeader String token) throws BookException {
        List<Cart> userCart = cartService.addMoreItems(bookId,token);
        return new ResponseEntity<>(new Response(200, "Successfully incremented book quantity",userCart), HttpStatus.OK);
    }
    @ApiOperation(value = "For getting all books in the cart")
    @GetMapping("/allbooks")
    public ResponseEntity<Response> getAllItemsFromCart (@RequestHeader String token) throws CartException {
            List<Cart>   userCart = cartService.getAllItemFromCart(token);
            return new ResponseEntity<>(new Response(200, "Successfully returned books from cart", userCart), HttpStatus.OK);
        }

    @ApiOperation(value = "For removing book from the cart")
    @DeleteMapping("/all")
    public ResponseEntity<Response> removeAllItem(@RequestHeader String token) throws CartException {
        String responseMessage = cartService.deleteAll(token);
        return new ResponseEntity<>(new Response(200, responseMessage), HttpStatus.OK);
    }

    @ApiOperation(value = "For decrementing the book quantity")
    @DeleteMapping("/item")
    public ResponseEntity<Response> subtractItem(@RequestParam Long bookId, @RequestHeader String token) {
        List<Cart> carts = cartService.subtractItem(bookId, token);
        return new ResponseEntity<>(new Response(200,"Book quantity decremented successfully", carts), HttpStatus.OK);
    }

    @ApiOperation(value = "For adding book to wishlist")
    @PostMapping("/wishlist/book")
    public Response addToWishList(@RequestParam Long bookId,@RequestHeader("token") String token) throws BookException {
        return cartService.addToWishList(bookId,token);
    }

    @ApiOperation(value = "For deleting book from wishlist")
    @DeleteMapping("/Wishlist/book")
    public ResponseEntity<Response> deleteFromWishlist(@RequestParam Long bookId,@RequestHeader("token") String token){
        List<Cart> cart = cartService.deleteFromWishlist(bookId, token);
        return  new ResponseEntity<>(new Response(200,"Book removed from wishlist",cart),HttpStatus.OK);
    }

    @ApiOperation(value = "For putting books wishlist to cart")
    @PutMapping("/wishlist/to/cart")
    public Response addFromWishlistToCart(@RequestParam Long bookId,@RequestHeader("token") String token){
        return cartService.addFromWishlistToCart(bookId,token);
    }

    @ApiOperation(value = "For getting all books from wishlist")
    @GetMapping("/wishlist/all")
    public List<Cart> getWishListBooks(@RequestHeader("token") String token) throws BookException {
        return cartService.getAllItemFromWishList(token);
    }

}

