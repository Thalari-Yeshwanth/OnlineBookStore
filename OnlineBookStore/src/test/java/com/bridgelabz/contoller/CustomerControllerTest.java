package com.bridgelabz.contoller;

import com.bridgelabz.controller.CustomerController;
import com.bridgelabz.dto.CustomerDto;
import com.bridgelabz.model.Customer;
import com.bridgelabz.response.Response;
import com.bridgelabz.service.CustomerService;
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


@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void mockitoRule() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenCustomerDto_WhenClickOnAddCustomerDetails_ShouldReturnResponse() {
        CustomerDto customerDto=new CustomerDto("Yeshwanth", "9666924586",515001,"abc", "19/451/2",
                "Anantapur", "AndraPradesh","Near AndraBank","Home");
        String token="asdfghjkasdfghjkl";
        String response="Customer details added successfully";
        Mockito.when(customerService.addCustomerDetails(token, customerDto)).thenReturn(response);
        ResponseEntity<Response> responseEntity = customerController.customerDetails(token, customerDto);
        Assert.assertEquals(responseEntity.getBody().getMessage(), response);
    }
    @Test
    public void givenToken_WhenClickOnGetCustomerDetails_ShouldReturnCustomerDetails() {
        CustomerDto customerDto=new CustomerDto("Yeshwanth", "9666924586",515001,"abc", "19/451/2",
                "Anantapur", "AndraPradesh","Near AndraBank","Home");
        Customer customer = new Customer(customerDto);
        String token="asdfghjkasdfghjkl";
        Mockito.when(customerService.getCustomerDetails(token)).thenReturn(customer);
        ResponseEntity<Response> responseEntity = customerController.getCustomerDetails(token);
        Assert.assertEquals(responseEntity.getBody().getData(), customer);
    }

}
