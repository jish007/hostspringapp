package com.carparking.project.service;
import com.carparking.project.entities.Slots;
import com.carparking.project.repository.ProfileRepository;
import com.carparking.project.repository.SlotsRepository;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BookingTimerService {
    private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    private final SlotsRepository slotsRepository;
    private final ProfileRepository profileRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public BookingTimerService(SlotsRepository bookingRepository, ProfileRepository profileRepository) {
        this.slotsRepository = bookingRepository;
        this.profileRepository = profileRepository;
        taskScheduler.initialize();
    }

    // Reload timers when the server starts
    @PostConstruct
    public void reloadTimersOnStartup() {
        List<Slots> activeBookings = slotsRepository.findAllActiveBookings(LocalDateTime.now().format(formatter));
        for (Slots booking : activeBookings) {
            scheduleBookingExpiry(booking);
        }
    }

    // Schedule expiry for a booking
    public void scheduleBookingExpiry(Slots booking) {
        LocalDateTime endDateTime = LocalDateTime.parse(booking.getExitTime(), formatter);
        long delayInMillis = Duration.between(LocalDateTime.now(), endDateTime).toMillis();

        if (delayInMillis > 0) {
            taskScheduler.schedule(() -> {
                System.out.println("Booking ID " + booking.getSlotId() + " expired.");
                expireBooking(booking);
            }, new java.util.Date(System.currentTimeMillis() + delayInMillis));
        }
    }

    private void expireBooking(Slots booking) {
        slotsRepository.expireOldBookings(LocalDateTime.now().format(formatter));
        profileRepository.expireOldProfile(LocalDateTime.now().format(formatter));
    }
}
