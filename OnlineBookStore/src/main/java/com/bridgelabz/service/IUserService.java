package com.bridgelabz.service;

import com.bridgelabz.dto.ForgotPasswordDto;
import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.RegistrationDto;
import com.bridgelabz.dto.ResetPasswordDto;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.response.Response;

public interface IUserService {

    boolean register(RegistrationDto registrationDto)throws UserException;

    String login(LoginDto loginDto) throws UserException;

    boolean verify(String token) throws UserException;

    Response forgetPassword(ForgotPasswordDto emailId);

    boolean resetPassword(ResetPasswordDto resetPassword, String token) throws UserException;


}
