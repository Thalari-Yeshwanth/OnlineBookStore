package com.bridgelabz.controller;

import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.RegistrationDto;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.response.Response;
import com.bridgelabz.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;


@RestController
@RequestMapping("/user")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UserController {

    @Autowired
    public IUserService userService;

    @Autowired
    private Environment environment;

    @ApiOperation("For registration")
    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody  RegistrationDto registrationDto) throws UserException{
          if (userService.register(registrationDto))
            return new ResponseEntity<>(new Response(200,"user register successful"), HttpStatus.OK);
        return new ResponseEntity<>(new Response(400, "user register unsuccessful"), HttpStatus.BAD_REQUEST);
    }

    @ApiOperation("For login")
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginDto loginDTO) throws UserException {
        String token = userService.login(loginDTO);
        if(token!=null) {
            return new ResponseEntity<>(new Response(200, "User login successful", token), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(400, "User login unsuccessful"), HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<Response> userVerification(@PathVariable("token") String token) throws UserException {

        if (userService.verify(token))
            return new ResponseEntity<>(new Response(200,"User verified succesfully"), HttpStatus.OK);

        return new ResponseEntity<>(new Response(400,"User verification failed"), HttpStatus.NOT_ACCEPTABLE);
    }

}
