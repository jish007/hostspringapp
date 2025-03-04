package com.carparking.project.service;

import com.carparking.project.entities.Slots;
import com.carparking.project.entities.User;
import com.carparking.project.repository.LoginRepository;
import com.carparking.project.repository.SuperAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class SuperAdminService {

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    private EmailService emailService;

    public List<Map<String, Object>> getJoinedData() {
        List<Object[]> results = superAdminRepository.findJoinedData();
        List<Map<String, Object>> formattedResults = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            Slots slot = (Slots) row[0];  // First element is Slots entity

            map.put("slotId", slot.getSlotId());
            map.put("slotNumber", slot.getSlotNumber());
            map.put("floor", slot.getFloor());
            map.put("vehicleType", slot.getVehicleType());
            map.put("propertyName", slot.getPropertyName());
            map.put("city", slot.getCity());
            map.put("district", slot.getDistrict());
            map.put("state", slot.getState());
            map.put("country", slot.getCountry());
            map.put("slotAvailability", slot.isSlotAvailability());
            map.put("googleLocation", slot.getGoogleLocation());
            map.put("adminName", slot.getAdminName());
            map.put("adminPhone", slot.getAdminPhone());
            map.put("propertyType", slot.getPropertyType());
            map.put("adminMailId", slot.getAdminMailId());
            map.put("vehicleNum", slot.getVehicleNum());

            // RoleStaging fields
            map.put("roleName", row[1]);
            map.put("responsibilities", row[2]);

            // Rates fields
            map.put("duration", row[3]);
            map.put("charge", row[4]);

            formattedResults.add(map);
        }
        return formattedResults;
    }

    public String acceptPropertyDetails(String email) throws Exception {
        User user = loginRepository.findByEmail(email);
        if(Objects.nonNull(user)){
            emailService.sendEmailAdmin(user);
            return "Successfully mailed";
        }
        else{
            throw new Exception("No User");
        }
    }

    public String rejectPropertyDetails(String email){
        return "";
    }
}

