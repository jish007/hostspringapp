package com.carparking.project.service;


import com.carparking.project.domain.ProfileDto;
import com.carparking.project.entities.Profile;
import com.carparking.project.entities.Slots;
import com.carparking.project.repository.ProfileRepository;
import com.carparking.project.repository.SlotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserApplicationServices {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    SlotsRepository slotsRepository;

    public String bookSlot(ProfileDto profileDto){
        Optional<Profile> optionalProfile = profileRepository.findById(profileDto.getVehicleNumber());
        Optional<Slots> optionalSlots = slotsRepository.findById(profileDto.getSlotId());

        if (optionalProfile.isPresent() && optionalSlots.isPresent()) {
            Profile profile = optionalProfile.get();
            Slots slots = optionalSlots.get();

            profile.setBookingDate(profileDto.getBookingDate());
            profile.setPaidStatus(true);
            profile.setPaidAmount(profileDto.getPaidAmount());
            profile.setAllocatedSlotNumber(profileDto.getAllocatedSlotNumber());
            profile.setParkedPropertyName(profileDto.getParkedPropertyName());
            profile.setDurationOfAllocation(profileDto.getDurationOfAllocation());
            profile.setPaymentDate(LocalDateTime.now().toString());
            profile.setAdminMailId(profileDto.getAdminMailId());
            profile.setTotalAmount(profile.getTotalAmount() + profileDto.getPaidAmount());
            profile.setBookingTime(profileDto.getBookingTime());
            profile.setBookingSource("App");
            profile.setEndtime(profileDto.getEndtime());
            profileRepository.save(profile);

            slots.setSlotAvailability(false);
            slots.setVehicleNum(profileDto.getVehicleNumber());
            slots.setStartTime(profileDto.getBookingTime());
            slots.setExitTime(profileDto.getEndtime());
            slotsRepository.save(slots);

            return "Slot and profile data updated successfully";
        } else {
            return "Slot or profile not found";
        }
    }
}
