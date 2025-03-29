package com.carparking.project.service;
import com.carparking.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendEmailAdmin(User login){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("007parkit@gmail.com");  // Replace with your email
        message.setTo(login.getEmail());
        message.setSubject("Welcome Admin to AutoSpaxe Family : Please find your credentials");
        message.setText("Your USERNAME is "+ login.getEmail()+" and your PASSWORD is "+ login.getPassWord());

        System.out.println("in mail sending");

        mailSender.send(message);

    }

    public void sendEmailUser(User login){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("007parkit@gmail.com");  // Replace with your email
        message.setTo(login.getEmail());
        message.setSubject("Welcome User : Please find your credential");
        message.setText("Your USERNAME is "+ login.getEmail()+" and your PASSWORD is "+ login.getPassWord());

        mailSender.send(message);

    }


    public void sendEmailfornoca(String admin){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("007parkit@gmail.com");  // Replace with your email
        message.setTo(admin);
        message.setSubject("inbuilt camera failed");
        message.setText("please attach the number plate manually");
        mailSender.send(message);

    }
}