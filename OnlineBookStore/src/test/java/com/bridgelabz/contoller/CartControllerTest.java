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
    public void givenCustomer_WhenClickClearCart_ShouldReturnResponse() {
        String response = "Items Removed Successfully";
        String token="abcd";
        Mockito.when(cartService.deleteAll(token)).thenReturn(response);
        ResponseEntity<Response> responseEntity = cartController.removeAllItem(token);
        Assert.assertEquals(responseEntity.getBody().getMessage(), response);
    }
    @Test
    public void givenCustomer_WhenClickRemoveItem_ShouldReturnCartList() throws BookException {
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

}