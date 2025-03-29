package com.carparking.project.service;

import com.carparking.project.entities.Profile;
import com.carparking.project.entities.Slots;
import com.carparking.project.helper.SlotsHelper;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AdruinoService {

    @Autowired
    private ImageService imageService;

    @Autowired
    private SlotsHelper slotsHelper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private SlotsService slotsService;


    public String getSlot(Map<String, String> requestParams) throws Exception {
        System.out.println("I am in" + requestParams);
        String vehicleno = imageService.getVehicleNumber();
        vehicleno = vehicleno.replaceAll("\\s+", "").toLowerCase();
        System.out.println(vehicleno);
        Optional<Profile> profile = Optional.ofNullable(profileService.getProfileByVehicleNumber(vehicleno));
        if (profile.isPresent()) {
            Profile profile1 = profileService.getProfileByVehicleNumber(vehicleno);
            return "1"+profile1.getUserName().toString();
        } else if (slotsService.isAvailableSlot()) {
            Slots slots = slotsHelper.parkCar(vehicleno);
            profileService.saveOnSiteProfile(vehicleno, slots, "ON-SITE");
            return "up"+slots.getSlotNumber();
        } else {
            return "0";
        }
        /*Slots slots=  slotsHelper.parkCar(vehicleno);
        String activeUser = slotsHelper.getActiveuser();
        if(StringUtils.isNullOrEmpty(vehicleno)){
            emailService.sendEmailfornoca(activeUser);
            profileService.saveOnSiteProfile(vehicleno,slots.getSlotNumber(),"ON-SITE");
            loginService.updateStatus("ECF",activeUser);
        }
        else{
            profileService.saveOnSiteProfile(vehicleno,slots.getSlotNumber(),"ON-SITE");
        }*/
    }
}