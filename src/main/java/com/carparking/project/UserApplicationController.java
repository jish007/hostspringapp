package com.carparking.project;

import com.carparking.project.domain.ProfileDto;
import com.carparking.project.domain.PropertyImage;
import com.carparking.project.service.UserApplicationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-app")
@CrossOrigin("*")
public class UserApplicationController {

    @Autowired
    UserApplicationServices userApplicationServices;
    @PostMapping("/slot-booking")
    public ResponseEntity<String> slotBooking(@RequestBody ProfileDto profileDto) {
        String response = userApplicationServices.bookSlot(profileDto);
        return ResponseEntity.ok(response);
    }
}
