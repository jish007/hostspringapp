package com.carparking.project;

import com.carparking.project.domain.ProfileDto;
import com.carparking.project.entities.Profile;
import com.carparking.project.entities.PropertyImageEntity;
import com.carparking.project.service.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profiles")
@CrossOrigin("*")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/save")
    public ResponseEntity<String> saveProfile(@RequestBody ProfileDto profileDto) throws Exception {
        String response = profileService.saveProfile(profileDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-admin-mail-id")
    public ResponseEntity<List<Profile>> getProfilesByAdminMailId(@RequestParam String adminMailId) {
        List<Profile> profileDtos = profileService.getProfilesByAdminMailId(adminMailId);
        return ResponseEntity.ok(profileDtos);
    }

    @GetMapping("/by-vehicle-number")
    public ResponseEntity<Profile> getProfileByVehicleNumber(@RequestParam String vehicleNumber) {
        Profile profile = profileService.getProfileByVehicleNumber(vehicleNumber);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/by-user-mail-id")
    public ResponseEntity<List<Profile>> getProfilesByUserMailId(@RequestParam String userEmailId) {
        List<Profile> profile = profileService.getProfileByUserEmail(userEmailId);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/timer")
    public ResponseEntity<Map<String, String>> getTimer() {
        Map<String, String> profile = profileService.getAllTimer();
        return ResponseEntity.ok(profile);
    }


    @GetMapping("/banned")
    public ResponseEntity<String> getTimer(@RequestParam String vehicleNumber) {
        String status = profileService.ban(vehicleNumber);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/sync")
    public ResponseEntity<String> sync() throws Exception {
        String status = profileService.updateProfile();
        return ResponseEntity.ok(status);
    }


    @GetMapping("/leave")
    public ResponseEntity<String> leaveslot(@RequestParam String slotNumber) throws Exception {
        return ResponseEntity.ok(profileService.leaveSlotFlow(slotNumber));
    }

}
