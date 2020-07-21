//package com.bridgelabz.utility;
//
////import com.bridgelabz.user.model.Email;
////import com.bridgelabz..user.model.Owner;
////import com.bridgelabz.user.model.User;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.mail.MailException;
////import org.springframework.mail.SimpleMailMessage;
////import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.validation.constraints.Email;
//
//@Service
//public class RegistrationMailService {
//
//    @Autowired
//    private  JwtGenerator jwtTokenUtility;
//
//    @Autowired
//    private JwtGenerator ownerJwtTokenUtility;
//
//    private JavaMailSender mailSender;
//
//    @Autowired
//    public RegistrationMailService(JavaMailSender mailSender) {
//
//        this.mailSender = mailSender;
//    }
//
//
//    public  void sendNotification(Email email) {
//        SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
//        simpleMailMessage.setTo(email.getEmailTo());
//        simpleMailMessage.setFrom("gangishettyvinay@gmail.com");
//        simpleMailMessage.setSubject(email.getSubject());
//        simpleMailMessage.setText(email.getMessage());
//
//        mailSender.send(simpleMailMessage);
//    }
//
//    public void sendNotification(User user) throws MailException{
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(user.getEmailId());
//        mailMessage.setFrom("gangishettyvinay@gmail.com");
//        mailMessage.setSubject("Thank you For Registering With Us : " + user.getFirstName()+user.getLastName());
//        mailMessage.setText("please click on the link to verify yourself : "+"http://localhost:8080/parkinglot/"+jwtTokenUtility.createToken(user.getUserId()));
//
//        mailSender.send(mailMessage);
//    }
//
//    public void sendNotification(Owner owner) throws MailException{
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(owner.getEmailId());
//        mailMessage.setFrom("gangishettyvinay@gmail.com");
//        mailMessage.setSubject("Thank you For Registering With Us : " + owner.getFirstName()+owner.getLastName());
//        mailMessage.setText("please click on the link to verify yourself : "+"http://localhost:8080/parkinglotowner/"+ownerJwtTokenUtility.createToken(owner.getOwnerId()));
//
//        mailSender.send(mailMessage);
//    }
//
//
//}
