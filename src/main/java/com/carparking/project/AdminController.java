package com.carparking.project;

import com.carparking.project.domain.UserDto;
import com.carparking.project.entities.User;
import com.carparking.project.service.ImageService;
import com.carparking.project.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    @Autowired
   private LoginService adminService;

    @PostMapping("/login")
    @CrossOrigin("*")
    public ResponseEntity<User> login(@RequestBody UserDto userDto) throws Exception {
        return ResponseEntity.ok(adminService.login(userDto));
    }

    @GetMapping("/logout")
    @CrossOrigin("*")
    public ResponseEntity<String> logout(@RequestParam String adminMailId) throws Exception {
        return ResponseEntity.ok(adminService.logout(adminMailId));
    }
}
