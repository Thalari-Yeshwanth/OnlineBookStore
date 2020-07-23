package com.bridgelabz.service;

import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.RegistrationDto;
import com.bridgelabz.exception.UserException;

public interface IUserService {

    boolean register(RegistrationDto registrationDto)throws UserException;

    String login(LoginDto loginDto) throws UserException;

    boolean verify(String token) throws UserException;
}
