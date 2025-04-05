package com.carparking.project.repository;

import com.carparking.project.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    List<Profile> findByAdminMailId(String adminMailId);

    Profile findByVehicleNumber(String vehicleNumber);

    List<Profile> findByUserEmailId(String userMail);

    Profile findByAllocatedSlotNumber(String allocatedSlotNumber);

    @Transactional
    @Modifying
    @Query("UPDATE Profile b SET b.allocatedSlotNumber = null, parkedPropertyName = null, adminMailId = null WHERE " +
            "CAST(b.endtime AS string) <= :now")
    int expireOldProfile(String now);
}
