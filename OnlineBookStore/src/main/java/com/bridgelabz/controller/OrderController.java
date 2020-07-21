package com.bridgelabz.controller;

import com.bridgelabz.exception.UserException;
import com.bridgelabz.model.Order;
import com.bridgelabz.response.Response;
import com.bridgelabz.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @ApiOperation("For placing order")
    @PostMapping("/place")
    public ResponseEntity<Response> placeOrder(@RequestHeader String token) throws UserException {
        Long orderId = orderService.placeOrder(token);
        Response response = new Response(200,"Order placed successfully", orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("For fetching order summary")
    @GetMapping("/details")
    public ResponseEntity<Response> getOrderSummary(@RequestHeader String token) throws UserException {
        Order orderDetails = orderService.getOrderSummary(token);
        Response response = new Response(200, "Response sent successfully", orderDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
