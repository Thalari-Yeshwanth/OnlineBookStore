package com.bridgelabz.service;

import com.bridgelabz.dto.CustomerDto;
import com.bridgelabz.model.Customer;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.repository.CustomerRepository;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.utility.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public CustomerRepository customerRepository;

    @Override
    public Customer getCustomerDetails(String token) {
        Long userId = JwtGenerator.decodeJWT(token);
        Optional<Customer> customer= customerRepository.findById(userId);
        return customer.get();
    }

    @Override
    public String addCustomerDetails(String token, CustomerDto customerDto) {
        Long userId= JwtGenerator.decodeJWT(token);
        Optional<UserModel> user = userRepository.findById(userId);
        Optional<Customer> isCustomerAvailable = customerRepository.findById(user.get().getUserId());
        if(isCustomerAvailable.isPresent()) {
            return "Your details are already saved";
        }
        Customer customer= new Customer(customerDto);
        customer.setCustomerId(userId);
        customerRepository.save(customer);
        return "Customer details added successfully";
    }
}
