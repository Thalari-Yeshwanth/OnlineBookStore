package com.bridgelabz.controller;

import com.bridgelabz.dto.ForgotPasswordDto;
import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.RegistrationDto;
import com.bridgelabz.dto.ResetPasswordDto;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.response.Response;
import com.bridgelabz.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UserController {

    @Autowired
    public IUserService userService;


    @ApiOperation("For registration")
    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody @Valid  RegistrationDto registrationDto) throws UserException{
          if (userService.register(registrationDto))
            return new ResponseEntity<>(new Response(200,"user register successful"), HttpStatus.OK);
        return new ResponseEntity<>(new Response(400, "user register unsuccessful"), HttpStatus.BAD_REQUEST);
    }

    @ApiOperation("For login")
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Valid LoginDto loginDTO) throws UserException {
        String token = userService.login(loginDTO);
        if(token!=null) {
            return new ResponseEntity<>(new Response(200, "User login successful", token), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(400, "User login unsuccessful"), HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<Response> userVerification(@PathVariable("token") String token) throws UserException {

        if (userService.verify(token))
            return new ResponseEntity<>(new Response(200,"User verified successfully"), HttpStatus.OK);

        return new ResponseEntity<>(new Response(400,"User verification failed"), HttpStatus.NOT_ACCEPTABLE);
    }
    @PostMapping("/forgot/password")
    public ResponseEntity<Response> forgotPassword(@RequestBody @Valid ForgotPasswordDto emailId) {

        Response response= userService.forgetPassword(emailId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/reset/password")
    public ResponseEntity<Response> resetPassword(@RequestBody @Valid ResetPasswordDto resetPassword,
                                                  @RequestHeader String token) throws UserException {
        
        if (userService.resetPassword(resetPassword, token))
            return new ResponseEntity<>(new Response(200,"User password reset successful"), HttpStatus.OK);

        return new ResponseEntity<>(new Response(400,"User password reset unsuccessful"), HttpStatus.NOT_ACCEPTABLE);
    }
}
