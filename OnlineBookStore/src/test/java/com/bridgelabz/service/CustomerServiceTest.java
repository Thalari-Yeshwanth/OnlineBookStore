package com.bridgelabz.service;

import com.bridgelabz.dto.CustomerDto;
import com.bridgelabz.model.Customer;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.repository.CustomerRepository;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.utility.JwtGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomerService customerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenCustomerDetails_WhenClickOnAddCustomerDetailsShouldReturnResponse() {

        String token = JwtGenerator.createJWT(1234567);
        Long userId= JwtGenerator.decodeJWT(token);
        CustomerDto customerDto= new CustomerDto("515001", "19/451/2,Raninagar", "Anantapur", "Near Andrabank", "Home");
        Optional<Customer> customer= Optional.of(new Customer(customerDto));
        Optional<UserModel> userDetails = Optional.of(new UserModel("Thalari Yeshwanth", "yeshwanththalari0123@gmaiil.com", "9666924586", "154G5a0124"));
        Mockito.when(userRepository.findById(userId)).thenReturn(userDetails);
        Mockito.when(customerRepository.findById(userId)).thenReturn(customer);
        String response = customerService.addCustomerDetails(token, customerDto);
        Assert.assertEquals("Customer details added successfully",response);
    }

    @Test
    public void givenCustomerDetails_WhenClickOnGetCustomerDetailsShouldReturnCustomerDetails() {
        String token = JwtGenerator.createJWT(1234567l);
        CustomerDto customerDto= new CustomerDto("515001", "19/451/2,Raninagar", "Anantapur", "Near Andrabank", "Home");
        Optional<Customer> customer= Optional.of(new Customer(customerDto));
        Mockito.when(customerRepository.findById(1234567L)).thenReturn(customer);
        Customer customerDetails = customerService.getCustomerDetails(token);
        Assert.assertEquals(customerDetails.getPinCode(), "515001");
    }
}