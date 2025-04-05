package com.carparking.project;

import com.carparking.project.domain.SlotsDto;
import com.carparking.project.entities.Slots;
import com.carparking.project.service.ProfileService;
import com.carparking.project.service.SlotsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SlotsController {

    @Autowired
    SlotsService slotsService;

    @Autowired
    ProfileService profileService;

    @GetMapping("/slots")
    @CrossOrigin("*")
    public ResponseEntity<List<Slots>> getAllSlots(@RequestParam String adminMailId){
        return ResponseEntity.ok(slotsService.getAllSlots(adminMailId));
    }

    @PostMapping("/slotEnroll")
    @CrossOrigin("*")
    public ResponseEntity<String> enrollSlots(@RequestBody SlotsDto slotsDto) throws Exception {
        return ResponseEntity.ok(slotsService.createSlots(slotsDto));
    }

    @GetMapping("/slotLeave")
    @CrossOrigin("*")
    public ResponseEntity<String> slotLeave(@RequestParam String slotsnumber) throws Exception {
        profileService.leaveSlotFlow(slotsnumber);
        return ResponseEntity.ok(slotsnumber + "Succefully left");
    }

}
