package com.carparking.project.repository;

import com.carparking.project.entities.PropertyImageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyImageRepository extends CrudRepository<PropertyImageEntity, Long> {
    List<PropertyImageEntity> findByPropertyName(String propertyName);

}

