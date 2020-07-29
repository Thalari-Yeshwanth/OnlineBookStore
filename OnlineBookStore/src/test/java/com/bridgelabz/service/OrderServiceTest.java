package com.bridgelabz.service;

import com.bridgelabz.dto.CustomerDto;
import com.bridgelabz.model.Cart;
import com.bridgelabz.model.Customer;
import com.bridgelabz.model.Order;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.repository.CartRepository;
import com.bridgelabz.repository.CustomerRepository;
import com.bridgelabz.repository.OrderRepository;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.utility.JwtGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void givenAConfirmOrderDetails_WhenWantToConfirm_ShouldReturnOrderId() {
        UserModel details=new UserModel(1L,"name","abc@gmail.com","7483247032","password",true,new ArrayList<>());
        List<Cart> cart=new ArrayList<>();
        Cart cart1=new Cart(1L,12L,12L,200.0,"TwoStates","JKRowling","http://", "abc",details,false);
        cart.add(cart1);
        CustomerDto customerDto=new CustomerDto("Yeshwanth", "9666924586",515001,"abc", "19/451/2",
                "Anantapur", "AndraPradesh","Near AndraBank","Home");
        Optional<Customer> customer  = Optional.of(new Customer(customerDto));
        double totalPrice= cart.stream().mapToDouble(book -> book.getPrice() * book.getQuantity()).sum();
        Mockito.when(userRepository.findById(details.getUserId())).thenReturn(Optional.of(details));
        Mockito.when(cartRepository.findByUserId(123456l)).thenReturn(cart);
        Mockito.when(customerRepository.findById(123456l)).thenReturn(customer);
        Order order=new Order(123456l, (long)123456, cart, totalPrice, customer.get());
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        String token = JwtGenerator.createJWT(123456);
        Long placeOrder = orderService.placeOrder(token);
        Assert.assertNotNull(placeOrder);
    }


    @Test
    public void given_WhenCustomerConfirmsTheOrder_ThenItShouldReturnOrderDetails(){
        List<Cart> cart=new ArrayList<>();
        CustomerDto customerDto=new CustomerDto("Yeshwanth", "9666924586",515001,"abc", "19/451/2",
                "Anantapur", "AndraPradesh","Near AndraBank","Home");
        Optional<Customer> customer  = Optional.of(new Customer(customerDto));
        UserModel details=new UserModel("name","abc@gmail.com","7483247032","password");
        Cart cart1=new Cart(1L,12L,12L,200.0,"TwoStates","JKRowling","http://", "abc",details,false);
        cart.add(cart1);
        new Order();
        Mockito.when(cartRepository.findByUserId(123456l)).thenReturn(cart);
        Mockito.when(customerRepository.findById(123456l)).thenReturn(customer);
        double totalPrice= cart.stream().mapToDouble(book -> book.getPrice() * book.getQuantity()).sum();
        Order userOrders=new Order(123456l, 123456l, cart, totalPrice, customer.get());
        Mockito.when(orderRepository.findByUserId(123456l)).thenReturn(Optional.of(userOrders));
        String token = JwtGenerator.createJWT(123456);
        Order orderSummary = orderService.getOrderSummary(token);
        Assert.assertEquals(orderSummary,userOrders);
    }

}