package com.carparking.project;

import com.carparking.project.domain.BookingDto;
import com.carparking.project.domain.ProfileDto;
import com.carparking.project.domain.PropertyImage;
import com.carparking.project.domain.RechargeRequest;
import com.carparking.project.service.ProfileService;
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

    @Autowired
    ProfileService profileService;

    @PostMapping("/slot-booking")
    public ResponseEntity<String> slotBooking(@RequestBody BookingDto bookingDto) throws Exception{
        String response = userApplicationServices.bookSlot(bookingDto);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/recharge")
    public ResponseEntity<String> recharge(@RequestBody RechargeRequest rechargeRequest) {
        return ResponseEntity.ok(profileService.rechargeProfile(rechargeRequest.getVehicleNumber(), rechargeRequest.getDuration(), rechargeRequest.getFare()));
    }
}
