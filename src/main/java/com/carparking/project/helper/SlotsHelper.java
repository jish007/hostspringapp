package com.carparking.project.helper;

import com.carparking.project.entities.Slots;
import com.carparking.project.service.SlotsService;
import com.carparking.project.repository.SlotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class SlotsHelper {

    @Autowired
    private SlotsService slotsService;

    @Autowired
    private SlotsRepository slotsRepository; // Injecting repository for DB updates

    private PriorityQueue<Slots> availableSlots; // Min-heap for available slots

    // Constructor
    public SlotsHelper() {
        this.availableSlots = new PriorityQueue<>();
    }

    // Get the active user's email
    public String getActiveUser() {
        return slotsService.getActiveUser();
    }

    // Allocate a parking slot to a car
    public Slots parkCar(String vehicleNum) {
        List<Slots> slots = slotsService.getAllSlots(getActiveUser());

        // Filter only available car slots
        availableSlots = new PriorityQueue<>(slots.stream()
                .filter(slot -> slot.isSlotAvailability() && "Car".equalsIgnoreCase(slot.getVehicleType()))
                .toList());

        if (availableSlots.isEmpty()) {
            return null; // No available slots
        }

        Slots slot = availableSlots.poll();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime currentTime = LocalDateTime.now();
        String startTime = currentTime.format(formatter);

        LocalDateTime exitDateTime = currentTime.plusHours(1);
        String exitTime = exitDateTime.format(formatter);

        slotsRepository.updateSlotAvailability(slot.getSlotId(), false, vehicleNum,startTime,exitTime);

        return slot;
    }

    // Remove a car from a parking slot
    public String leaveSlot(Slots slot) {
        if (!slot.isSlotAvailability()) {
            slotsRepository.updateSlotAvailability(slot.getSlotId(), true, null,null,null);
            return "Slot " + slot.getSlotNumber() + " is now available";
        }
        return "Slot is already empty or invalid";
    }
}
