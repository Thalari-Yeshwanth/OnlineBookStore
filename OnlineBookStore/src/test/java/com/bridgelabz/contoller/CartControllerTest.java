package com.bridgelabz.contoller;


import com.bridgelabz.controller.CartController;
import com.bridgelabz.exception.BookException;
import com.bridgelabz.exception.CartException;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.model.Book;
import com.bridgelabz.model.Cart;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.response.Response;
import com.bridgelabz.service.CartService;
import com.bridgelabz.utility.JwtGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {
    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    public void mockitoRule() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenCustomer_WhenClickOnAddToBag_ShouldReturnResponse() throws BookException, UserException {
        String response = "book added to cart successfully";
        String token = "abcd";
        Long bookId = 1L;
        Mockito.when(cartService.addToCart(token, bookId)).thenReturn(response);
        ResponseEntity<Response> responseEntity = cartController.addToCart(bookId, token);
        Assert.assertEquals(responseEntity.getBody().getMessage(), response);


    }
    @Test
    public void givenCustomer_WhenClickOnAddToWishList_ShouldReturnResponse() throws BookException, UserException {
        Response response = new Response(HttpStatus.OK.value(), "Book added to WishList");
        String token = "abcd";
        Long bookId = 1L;
        Mockito.when(cartService.addToWishList(bookId, token)).thenReturn(response);
        Response response1 = cartController.addToWishList(bookId, token);
        Assert.assertEquals(response1, response);
    }

    @Test
    public void givenCustomer_WhenClickRemoveItemFromWishList_ShouldReturnWishListList() throws BookException {
        String token="abcd";Long bookId=1L;
        List<Cart> cartList=new ArrayList<>();
        Book book=new Book("1",1L,"JK Rowling","Two States","Two States", 1, 200.0,"abc");
        Cart cart=new Cart(book);
        cartList.add(cart);
        List<Cart> selectedItems = new ArrayList<>();
        Mockito.when(cartService.deleteFromWishlist(bookId,token)).thenReturn(selectedItems);
        ResponseEntity<Response> responseEntity = cartController.deleteFromWishlist(bookId, token);
        Assert.assertEquals(responseEntity.getBody().getData(), selectedItems);
    }

    @Test
    public void givenCustomer_WhenClickOnMoveBookToCart_ShouldReturnResponse() {
        String token="abcd";Long bookId=1L;
        Response expectedResponse = new Response(HttpStatus.OK.value(), "Successfully added book to cart from wishlist");
        when(cartService.addFromWishlistToCart(bookId, token)).thenReturn(expectedResponse);
        Response actualResponse = cartController.addFromWishlistToCart(bookId, token);
        Assert.assertEquals(expectedResponse,actualResponse);

    }



    @Test
    public void givenCustomer_WhenClickClearCart_ShouldReturnResponse() {
        String response = "Items Removed Successfully";
        String token="abcd";
        Mockito.when(cartService.deleteAll(token)).thenReturn(response);
        ResponseEntity<Response> responseEntity = null;
        try {
            responseEntity = cartController.removeAllItem(token);
            Assert.assertEquals(responseEntity.getBody().getMessage(), response);
        } catch (CartException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenCustomer_WhenClickRemoveItem_ShouldReturnCartList() throws BookException, CartException {
        String token="abcd";Long bookId=1L;
        List<Cart> cartList=new ArrayList<>();
        Book book=new Book("1",1L,"JK Rowling","Two States","Two States", 1, 200.0,"abc");
        Cart cart=new Cart(book);
        cartList.add(cart);
        List<Cart> selectedItems = new ArrayList<>();
        Mockito.when(cartService.removeItem(bookId,token)).thenReturn(selectedItems);
        ResponseEntity<Response> responseEntity = cartController.removeFromCart(bookId,token);
        Assert.assertEquals(responseEntity.getBody().getData(), selectedItems);

    }

    @Test
    public void givenCustomer_WhenClickCnMyCart_ShouldReturnCartList() throws CartException {
        String token="abcd";
        List<Cart> cartList=new ArrayList<>();
        Book book=new Book("1",1L,"JK Rowling","Two States","Two States", 1, 200.0,"abc");
        Cart cart=new Cart(book);
        cartList.add(cart);
        Mockito.when(cartService.getAllItemFromCart(token)).thenReturn(cartList);
        ResponseEntity<Response> responseEntity = cartController.getAllItemsFromCart(token);
        Assert.assertEquals(responseEntity.getBody().getData(), cartList);
    }

    @Test
    public void givenCustomer_WhenClickCnMyWishList_ShouldReturnWishList() throws CartException, BookException {
        String token="abcd";
        List<Cart> cartList=new ArrayList<>();
        Book book=new Book("1",1L,"JK Rowling","Two States","Two States", 1, 200.0,"abc");
        Cart cart=new Cart(book);
        cartList.add(cart);
        Mockito.when(cartService.getAllItemFromWishList(token)).thenReturn(cartList);
        List<Cart> wishListBooks = cartController.getWishListBooks(token);
        Assert.assertEquals(wishListBooks, cartList);
    }

    @Test
    public void givenCustomer_WhenClickOnAddQuantity_ShouldReturnUpdatedCartList() throws  BookException {
        String token="abcd";Long bookId=1L;
        List<Cart> cartList=new ArrayList<>();
        Book book=new Book("1",1L,"JK Rowling","Two States","Two States", 1, 200.0,"abc");
        Cart cart=new Cart(book);
        cartList.add(cart);
        List<Cart> selectedItems = cartList.stream().filter(cartItem -> cartItem.getBookId().equals(bookId))
                .collect(Collectors.toList());
        for (Cart item:selectedItems) {
            item.setQuantity(book.getQuantity()-1);
        }
        Mockito.when(cartService.addMoreItems(bookId,token)).thenReturn(selectedItems);
        ResponseEntity<Response> responseEntity = cartController.addMoreItems(bookId,token);
        Assert.assertEquals(responseEntity.getBody().getData(), selectedItems);
    }
    @Test
    public void givenCustomer_WhenClickOnSubQuantity_ShouldReturnUpdatedCartList() {
        String token="abcd";Long bookId=1L;
        List<Cart> cartList=new ArrayList<>();
        Book book=new Book("1",1L,"JK Rowling","Two States","Two States", 1, 200.0,"abc");
        Cart cart=new Cart(book);
        cartList.add(cart);
        List<Cart> selectedItems = cartList.stream().filter(cartItem -> cartItem.getBookId().equals(bookId))
                .collect(Collectors.toList());
        for (Cart item:selectedItems) {
            item.setQuantity(book.getQuantity()-1);
        }
        Mockito.when(cartService.subtractItem(bookId,token)).thenReturn(selectedItems);
        ResponseEntity<Response> responseEntity = cartController.subtractItem(bookId,token);
        Assert.assertEquals(responseEntity.getBody().getData(), selectedItems);
    }
    @Test
    public void givenCart_WhenCartIsEmpty_ShouldReturnException() {
        String token = JwtGenerator.createJWT(1234567);
        CartException expectedException ;
        expectedException= new CartException("Cart is empty", CartException.ExceptionType.EMPTY_CART);
        try {
            when(cartService.getAllItemFromCart(token)).thenThrow(expectedException);
            cartController.getAllItemsFromCart(token);
        } catch (CartException e) {
            Assert.assertEquals(CartException.ExceptionType.EMPTY_CART,e.type);
        }
    }

    @Test
    public void whenBookIsAddedToCart_ThrowException() {

    }
}