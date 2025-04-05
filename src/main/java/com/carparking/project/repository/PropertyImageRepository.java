package com.carparking.project.repository;

import com.carparking.project.entities.PropertyImageEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PropertyImageRepository extends CrudRepository<PropertyImageEntity, Long> {
    List<PropertyImageEntity> findByPropertyName(String propertyName);

    @Modifying
    @Transactional
    @Query("UPDATE PropertyImageEntity p SET p.verified = :verified WHERE p.adminMailId = :adminMailId")
    void updateVerified(String adminMailId, boolean verified);

}

