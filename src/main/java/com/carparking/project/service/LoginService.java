package com.carparking.project.service;

import com.carparking.project.domain.UserDto;
import com.carparking.project.entities.User;
import com.carparking.project.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private EmailService emailService;


    public User login(UserDto userDto) throws Exception {
        User user = loginRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
        if (Objects.nonNull(user)) {
            user.setActive("ACTIVE");
            loginRepository.save(user);
            return user;
        } else {
            throw new Exception("Invalid Credential");
        }
    }

    public void updateStatus(String errorcode, String email) {
        Optional<User> login = loginRepository.findById(email);
        login.get().setRemarks(errorcode);
        loginRepository.save(login.get());
    }

    public String signUp(UserDto userDto, String ADMIN_USER) throws Exception {
        userDto.setRoleName(ADMIN_USER);
        userDto.setActive("INACTIVE");
        User user = loginRepository.save(new User(userDto));
        if (Objects.nonNull(user) && "ADMIN_USER".equals(ADMIN_USER)) {
            return "Admin User Is Created";
        }
        if(Objects.nonNull(user)&& "USER".equals(ADMIN_USER)){
            emailService.sendEmailUser(user);
            return "User Login Created";
        }
        else {
            throw new Exception("Operation Failed");
        }
    }

    public String logout(String email) {
        User login = loginRepository.findByEmail(email);
        login.setActive("INACTIVE");
        loginRepository.save(login);
        return "logout succesfull";
    }
}
