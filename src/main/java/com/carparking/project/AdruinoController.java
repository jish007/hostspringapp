package com.carparking.project;

import com.carparking.project.entities.Slots;
import com.carparking.project.helper.SlotsHelper;
import com.carparking.project.service.*;
import com.mysql.cj.util.StringUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Test Controller", description = "Example API for Swagger integration")
public class AdruinoController {


    @Autowired
    AdruinoService adruinoService;

    @Autowired
    ProfileService profileService;

    @PostMapping(value = "/updateSensor", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> updateSensorData(@RequestParam Map<String, String> requestParams) throws Exception {
        String response = adruinoService.getSlot(requestParams);
        System.out.println(response);
        return  ResponseEntity.ok(response.toString().trim());  // Trim extra spaces
    }
}