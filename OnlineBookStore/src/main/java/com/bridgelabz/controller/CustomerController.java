package com.bridgelabz.controller;

import com.bridgelabz.dto.CustomerDto;
import com.bridgelabz.model.Customer;
import com.bridgelabz.response.Response;
import com.bridgelabz.service.ICustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CustomerController {

    @Autowired
    public ICustomerService customerService;

    @ApiOperation("For adding customer details")
    @PostMapping("/details")
    public ResponseEntity<Response> customerDetails(@RequestHeader(value = "token", required = false)String token, @RequestBody CustomerDto customer) {
        String responseMessage = customerService.addCustomerDetails(token, customer);
        return new ResponseEntity<>(new Response(200,responseMessage), HttpStatus.OK);
    }

    @ApiOperation("For fetching customer details")
    @GetMapping("/details")
    public ResponseEntity<Response> getCustomerDetails(@RequestHeader(value = "token")String token) {
        Customer userDetails= customerService.getCustomerDetails(token);
        Response response=new Response(200,"Customer details sent successfully", userDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
