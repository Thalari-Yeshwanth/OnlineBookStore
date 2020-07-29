package com.bridgelabz.service;

import com.bridgelabz.dto.ForgotPasswordDto;
import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.RegistrationDto;
import com.bridgelabz.dto.ResetPasswordDto;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.response.Response;
import com.bridgelabz.utility.JwtGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static com.bridgelabz.exception.UserException.ExceptionType.INVALID_USER;

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
        RegistrationDto registrationDto1 = new RegistrationDto("Thalari Yeshwanth", "yeshwanthranganath14@gmaiil.com", "9666924586", "154G5a0124");
        Optional<UserModel> userDetails = Optional.of(new UserModel("Thalari Yeshwanth", "yeshwanththalari0123@gmaiil.com", "9666924586", "154G5a0124"));
        Mockito.when(userRepository.findByEmail(registrationDto1.getEmailId())).thenReturn(userDetails);
        registrationDto1.getFullName();registrationDto1.getMobileNumber();registrationDto1.getPassword();registrationDto1.getEmailId();
        boolean register = userService.register(registrationDto1);
        System.out.println(register);
    }

    @Test
    public void whenUserEnterTheEmailAndPassword_ShouldReturnToken() throws UserException {
        LoginDto loginDto = new LoginDto("thalariyeswanth", "password");
        Optional<UserModel> userDetails = Optional.of(new UserModel("Thalari Yeshwanth", "yeshwanththalari0123@gmaiil.com", "9666924586", "154G5a0124"));
        UserModel userDetails1 = new UserModel("Thalari Yeshwanth", "yeshwanththalari0123@gmaiil.com", "9666924586", "154G5a0124");
        Mockito.when(userRepository.findByEmail(loginDto.getEmailId())).thenReturn(userDetails);
        Mockito.when(bCryptPasswordEncoder.matches(loginDto.getPassword(), userDetails.get().getPassword())).thenReturn(true);
        Mockito.when(userRepository.save(userDetails1)).thenReturn(userDetails1);
        String login = userService.login(loginDto);
        Assert.assertNotNull(login);
    }

    @Test
    public void whenUserForgotPassword_clickOnForgotPassword_ShouldReturnResponse() {
        ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto();
        forgotPasswordDto.setEmailId("yeshwanththalari.pk@gmail.com");
        Optional<UserModel> user = Optional.of(new UserModel(1l, "Yeshwanth Thalari", "yeshwanththalari.pk@gmail.com", "9666924586", "1654G5a0123@", true, new ArrayList<>()));
        Mockito.when(userRepository.findByEmail("yeshwanththalari.pk@gmail.com")).thenReturn(user);
        Response response = userService.forgetPassword(forgotPasswordDto);
        Assert.assertEquals(response.getMessage(), "ResetPassword link Successfully");
    }

    @Test
    public void GivenUserWhenNotVerifiedClickOnForgotPassword_ShouldReturnResponse() {
        ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto();
        forgotPasswordDto.setEmailId("yeshwanththalari.pk@gmail.com");
        Optional<UserModel> user = Optional.of(new UserModel(1l, "Yeshwanth Thalari", "yeshwanththalari.pk@gmail.com", "9666924586", "1654G5a0123@", false, new ArrayList<>()));
        Mockito.when(userRepository.findByEmail("yeshwanththalari.pk@gmail.com")).thenReturn(user);
        Response response = userService.forgetPassword(forgotPasswordDto);
        Assert.assertEquals(response.getMessage(), "Email sending failed");
    }

    @Test
    public void whenUserClickOnResetPassword_ShouldReturnTrue() throws UserException {
        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setConfirmPassword("134G5a0123@");
        resetPasswordDto.setNewPassword("134G5a0123@");
        Optional<UserModel> user = Optional.of(new UserModel(1l, "Yeshwanth Thalari", "yeshwanththalari.pk@gmail.com", "9666924586", "1654G5a0123@", true, new ArrayList<>()));
        Mockito.when(userRepository.findById(1L)).thenReturn(user);
        String token = JwtGenerator.createJWT(1L);
        boolean isPasswordUpdated = userService.resetPassword(resetPasswordDto, token);
        Assert.assertTrue(isPasswordUpdated);
    }
    @Test
    public void GivenUserWhenNotRegisterClickOnResetPassword_ShouldReturnException() {
        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setConfirmPassword("134G5a0123@");
        resetPasswordDto.setNewPassword("134G5a0123@");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(new UserModel()));
        String token = JwtGenerator.createJWT(1L);
        try {
            userService.resetPassword(resetPasswordDto, token);
        } catch (UserException e) {
            Assert.assertEquals(e.type, INVALID_USER);

        }
    }


    @Test
    public void GivenWrongPassword_whenUserClickOnResetPassword_ShouldReturnFalse() throws UserException {
        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setConfirmPassword("134G5a0123@");
        resetPasswordDto.setNewPassword("144G5a0123@");
        String token = JwtGenerator.createJWT(1L);
        boolean isPasswordUpadted = userService.resetPassword(resetPasswordDto, token);
        Assert.assertFalse(isPasswordUpadted);
    }
    @Test
    public void givenUserRegisteredWhenClickOnVerify_shouldReturnTrue() throws UserException {
        Optional<UserModel> user = Optional.of(new UserModel(1l, "Yeshwanth Thalari", "yeshwanththalari.pk@gmail.com", "9666924586", "1654G5a0123@", false, new ArrayList<>()));
        Mockito.when(userRepository.findById(1L)).thenReturn(user);
        String token=JwtGenerator.createJWT(1L);
        boolean isVerified = userService.verify(token);
        Assert.assertTrue(isVerified);
    }
    @Test
    public void givenUserNotRegisteredWhenClickOnVerify_shouldReturnFalse() throws UserException {
        Optional<UserModel> user = Optional.of(new UserModel(-1L, "Yeshwanth Thalari", "yeshwanththalari.pk@gmail.com", "9666924586", "1654G5a0123@", false, new ArrayList<>()));
        Mockito.when(userRepository.findById(-1L)).thenReturn(user);
        String token=JwtGenerator.createJWT(-1L);
        boolean isVerified = userService.verify(token);
        Assert.assertFalse(isVerified);
    }
}
