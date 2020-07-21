package com.bridgelabz.service;

import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.RegistrationDto;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.utility.JwtGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean register(RegistrationDto registrationDto) {
        Optional<UserModel> isEmailAvailable = userRepository.findByEmail(registrationDto.getEmailId());
        if (isEmailAvailable.isPresent()) {
            return false;
        }
        UserModel userDetails = new UserModel();
        BeanUtils.copyProperties(registrationDto, userDetails);
        userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        userRepository.save(userDetails);
        return true;
    }

    @Override
    public String login(LoginDto loginDto) throws UserException {
        Optional<UserModel> userCheck = userRepository.findByEmail(loginDto.getEmailId());
        if (!userCheck.isPresent()) {
            throw new UserException("No user found", UserException.ExceptionType.INVALID_USER);
        }
        if (bCryptPasswordEncoder.matches(loginDto.getPassword(), userCheck.get().getPassword())) {
            String token = JwtGenerator.createJWT(userCheck.get().getUserId());
            userRepository.save(userCheck.get());
            return token;
        }
        throw  new UserException("Incorrect credentials", UserException.ExceptionType.INVALID_CREDENTIALS);
    }
}
