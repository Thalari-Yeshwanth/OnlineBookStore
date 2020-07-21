package com.bridgelabz.service;

import com.bridgelabz.dto.CustomerDto;
import com.bridgelabz.model.Customer;
import com.bridgelabz.model.UserModel;

public interface ICustomerService {

    Customer getCustomerDetails(String token);

    String addCustomerDetails(String token, CustomerDto customerDetails);
}
