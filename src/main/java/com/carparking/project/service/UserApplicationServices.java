package com.carparking.project.service;


import com.carparking.project.domain.BookingDto;
import com.carparking.project.domain.ProfileDto;
import com.carparking.project.entities.Profile;
import com.carparking.project.entities.Slots;
import com.carparking.project.repository.ProfileRepository;
import com.carparking.project.repository.SlotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class UserApplicationServices {

    @Autowired
    private BookingTimerService bookingTimerService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    SlotsRepository slotsRepository;

    public String bookSlot(BookingDto bookingDto) throws Exception{
        Optional<Profile> optionalProfile = profileRepository.findById(bookingDto.getVehicleNumber());
        Optional<Slots> optionalSlots = slotsRepository.findById(bookingDto.getSlotId());

        if (optionalProfile.isPresent() && optionalSlots.isPresent()) {
            Profile profile = optionalProfile.get();
            Slots slots = optionalSlots.get();

            profile.setBookingDate(bookingDto.getBookingDate());
            profile.setPaidStatus(true);
            profile.setPaidAmount(bookingDto.getPaidAmount());
            profile.setAllocatedSlotNumber(slots.getSlotNumber());
            profile.setParkedPropertyName(slots.getPropertyName());
            profile.setDurationOfAllocation(bookingDto.getDurationOfAllocation());
            profile.setPaymentDate(bookingDto.getBookingDate());
            profile.setAdminMailId(slots.getAdminMailId());
            profile.setTotalAmount(profile.getTotalAmount() + bookingDto.getPaidAmount());
            profile.setBookingTime(bookingDto.getBookingTime());
            profile.setBookingSource(bookingDto.getBookingSource());
            profile.setEndtime(bookingDto.getEndtime());
            profileRepository.save(profile);

            slots.setSlotAvailability(false);
            slots.setVehicleNum(bookingDto.getVehicleNumber());
            slots.setStartTime(bookingDto.getBookingDate());
            slots.setExitTime(bookingDto.getEndtime());
            slotsRepository.save(slots);

            bookingTimerService.scheduleBookingExpiry(slots);

            return "Slot and profile data updated successfully";
        } else {
            throw new Exception("Slot or profile not found");
        }
    }
}
