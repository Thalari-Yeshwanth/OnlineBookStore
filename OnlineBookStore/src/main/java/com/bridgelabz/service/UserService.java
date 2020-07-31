package com.bridgelabz.service;

import com.bridgelabz.dto.ForgotPasswordDto;
import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.RegistrationDto;
import com.bridgelabz.dto.ResetPasswordDto;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.response.EmailObject;
import com.bridgelabz.response.Response;
import com.bridgelabz.utility.JwtGenerator;
import com.bridgelabz.utility.RabbitMQSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RabbitMQSender rabbitMQSender;


    private static final String VERIFICATION_URL = "http://localhost:3000/verify/";
    private static final String RESETPASSWORD_URL = "http://localhost:8080/user/resetpassword?token=";


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
        String response= getResponse(userDetails.getUserId());
        if (rabbitMQSender.send(new EmailObject(registrationDto.getEmailId(), "Registration Link...", response)))
            return true;
        return true;
    }

    private String getResponse(long userId) {
        String response="\t\t\t\t\t\tThanking you for Registartion with us\n\n"+"Click on the below link for the verification\n\n"
                +VERIFICATION_URL + JwtGenerator.createJWT(userId);
        return  response;
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

    @Override
    public boolean verify(String token) throws UserException {
        long id = JwtGenerator.decodeJWT(token);
        UserModel userInfo = userRepository.findById(id).get();
        if (id > 0 && userInfo != null) {
            if (!userInfo.isVerify()) {
                userInfo.setVerify(true);
                userRepository.save(userInfo);
                return true;
            }
            throw new UserException("User already verified", UserException.ExceptionType.ALREADY_VERFIED);
        }
        return false;
    }
    @Override
    public Response forgetPassword(ForgotPasswordDto userMail) {
        UserModel isIdAvailable = userRepository.findByEmail(userMail.getEmailId()).get();
        if (isIdAvailable != null && isIdAvailable.isVerify()) {
            String token = JwtGenerator.createJWT(isIdAvailable.getUserId());
            String response = RESETPASSWORD_URL + token;
            if (rabbitMQSender.send(new EmailObject(isIdAvailable.getEmailId(), "ResetPassword Link...", response)))
                return new Response(HttpStatus.OK.value(), "ResetPassword link Successfully", token);
        }
        return new Response(HttpStatus.OK.value(), "Email sending failed");
    }

    @Override
    public boolean resetPassword(ResetPasswordDto resetPassword, String token) throws UserException {
        if (resetPassword.getNewPassword().equals(resetPassword.getConfirmPassword())) {
            long id = JwtGenerator.decodeJWT(token);
            UserModel isIdAvailable = userRepository.findById(id).get();
            if (isIdAvailable != null) {
                isIdAvailable.setPassword(bCryptPasswordEncoder.encode((resetPassword.getNewPassword())));
                userRepository.save(isIdAvailable);
                return true;
            }
            throw new UserException("User not exist", UserException.ExceptionType.INVALID_USER);
        }
        return false;
    }

}
