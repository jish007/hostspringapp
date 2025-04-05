package com.carparking.project;

import com.carparking.project.entities.Profile;
import com.carparking.project.helper.RateHelper;
import com.carparking.project.repository.ProfileRepository;
import com.carparking.project.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
public class RatesController {

    @Autowired
    RateHelper rateHelper;
    @Autowired
    ProfileService profileService;
    @Autowired
    ProfileRepository profileRepository;


    @GetMapping("/paymentdetails")
    public ResponseEntity<Map<String, Double>> getPayment(@RequestParam String vehicleNumber) {
        Profile profile = profileRepository.findByVehicleNumber(vehicleNumber);
        String duration = profileService.duration(LocalDateTime.now(), LocalDateTime.parse(profile.getEndtime(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        return ResponseEntity.ok(rateHelper.findtotalrate(duration));

    }
}