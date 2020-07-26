package com.bridgelabz.contoller;
import com.bridgelabz.controller.OrderController;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.model.*;
import com.bridgelabz.response.Response;
import com.bridgelabz.service.CustomerService;
import com.bridgelabz.service.OrderService;
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

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void givenAConfirmOrderDetails_WhenWantToConfirm_ShouldReturnOrderId() {
        try {
            Mockito.when(orderService.placeOrder("asdfghjkasdfghjkl")).thenReturn(Long.valueOf("123456"));
            Response body = orderController.placeOrder("asdfghjkasdfghjkl").getBody();
            Assert.assertEquals(123456l,body.getData());
        } catch ( UserException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void given_WhenCustomerConfirmsTheOrder_ThenITShouldReturnOrderDetails() throws UserException {
        List<Cart> cart=new ArrayList<>();
        Long orderId= orderService.generateOrderId();
        List<UserModel> userDetails=new ArrayList<>();
        UserModel details=new UserModel("name","abc@gmail.com","7483247032","password");
        userDetails.add(details);
        Cart cart1=new Cart(1L,12L,12L,200.0,"TwoStates","JKRowling","http://", "abc",details,false);
        cart.add(cart1);
        double totalPrice= cart.stream().mapToDouble(book -> book.getPrice() * book.getQuantity()).sum();
        Customer customer=new Customer();
        Order order=new Order(orderId, (long)123456, cart, totalPrice, customer);
        Mockito.when(orderService.getOrderSummary("asdfghjkasdfghjkl")).thenReturn(order);
        ResponseEntity<Response> respone = orderController.getOrderSummary("asdfghjkasdfghjkl");
        Assert.assertEquals(respone.getBody().getData(),order);
    }

}