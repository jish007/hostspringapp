package com.carparking.project.service;

import com.carparking.project.repository.ProfileRepository;
import com.carparking.project.repository.SlotsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BookingBatchService {
    private final SlotsRepository bookingRepository;
    private final ProfileRepository profileRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public BookingBatchService(SlotsRepository bookingRepository,ProfileRepository profileRepository) {
        this.bookingRepository = bookingRepository;
        this.profileRepository = profileRepository;
    }

    // Runs every 1 minute to expire old bookings
    @Scheduled(fixedRate = 180000) // 1 minute = 60,000 ms
    public void checkAndExpireBookings() {
        int updatedRows = bookingRepository.expireOldBookings(LocalDateTime.now().format(formatter));
        profileRepository.expireOldProfile(LocalDateTime.now().format(formatter));
        if (updatedRows > 0) {
            System.out.println("Expired " + updatedRows + " bookings.");
        }
    }
}

