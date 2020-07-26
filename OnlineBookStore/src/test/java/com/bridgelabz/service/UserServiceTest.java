package com.bridgelabz.service;

import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.RegistrationDto;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void givenUserDetails_WhenRegisterShouldReturnSuccessfulMessage() {
        RegistrationDto registrationDto1=new RegistrationDto("Thalari Yeshwanth", "yeshwanthranganath14@gmaiil.com", "9666924586", "154G5a0124");
        Optional<UserModel> userDetails = Optional.of(new UserModel("Thalari Yeshwanth", "yeshwanththalari0123@gmaiil.com", "9666924586", "154G5a0124"));
        Mockito.when(userRepository.findByEmail(registrationDto1.getEmailId())).thenReturn(userDetails);
        //registrationDto1.getFullName();registrationDto1.getMobileNumber();registrationDto1.getPassword();registrationDto1.getEmailId();
        boolean register = userService.register(registrationDto1);
        System.out.println(register);
    }

    @Test
    public void whenUserEnterTheEmailAndPassword_ShouldReturnToken() throws UserException {
        LoginDto loginDto = new LoginDto("thalariyeswanth", "password");
        Optional<UserModel> userDetails = Optional.of(new UserModel("Thalari Yeshwanth", "yeshwanththalari0123@gmaiil.com", "9666924586", "154G5a0124"));
        UserModel userDetails1 = new UserModel("Thalari Yeshwanth", "yeshwanththalari0123@gmaiil.com", "9666924586", "154G5a0124");
        Mockito.when(userRepository.findByEmail(loginDto.getEmailId())).thenReturn(userDetails);
        Mockito.when(bCryptPasswordEncoder.matches(loginDto.getPassword(),userDetails.get().getPassword())).thenReturn(true);
        Mockito.when(userRepository.save(userDetails1)).thenReturn(userDetails1);
        String login = userService.login(loginDto);
        Assert.assertNotNull(login);
    }
}
