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
import com.bridgelabz.response.EmailObject;
import com.bridgelabz.utility.JwtGenerator;
import com.bridgelabz.utility.MailData;
import com.bridgelabz.utility.RabbitMQSender;
import org.junit.Assert;
import org.junit.Before;
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
import static org.mockito.Mockito.when;


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

    @Mock
    private MailData mailData;

    @Mock
    private RabbitMQSender rabbitMQSender;

    @InjectMocks
    private OrderService orderService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenAConfirmOrderDetails_WhenWantToConfirm_ShouldReturnOrderId() {
        UserModel details=new UserModel(1L,"name","abc@gmail.com","7483247032","password",true,new ArrayList<>());
        List<Cart> cart=new ArrayList<>();
        Cart cart1=new Cart(1L,12L,12L,200.0,"TwoStates","JKRowling","http://", "abc",details,false);
        cart.add(cart1);
        CustomerDto customerDto=new CustomerDto("Yeshwanth", "9666924586",515001,"abc", "19/451/2", "Anantapur", "AndraPradesh","Near AndraBank","Home");
        Optional<Customer> customer  = Optional.of(new Customer(customerDto));
        double totalPrice= cart.stream().mapToDouble(book -> book.getPrice() * book.getQuantity()).sum();
        Mockito.when(userRepository.findById(details.getUserId())).thenReturn(Optional.of(details));
        String token = JwtGenerator.createJWT(123456);
        Long userId=JwtGenerator.decodeJWT(token);
        Mockito.when(cartRepository.findByUserId(123456l)).thenReturn(cart);
        Mockito.when(customerRepository.findByUserId(userId)).thenReturn(customer);
        Long orderId= orderService.generateOrderId();
        Order order=new Order(orderId,userId, cart, totalPrice, customer.get());
        String orderMail = mailData.getOrderMail(orderId, customer.get(), totalPrice, cart);
        when(orderRepository.save(order)).thenReturn(order);
        when( mailData.getOrderMail(orderId, customer.get(), totalPrice, cart)).thenReturn(orderMail);
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