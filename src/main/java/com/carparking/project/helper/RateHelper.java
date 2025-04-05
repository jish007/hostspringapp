package com.carparking.project.helper;

import com.carparking.project.entities.Rates;
import com.carparking.project.repository.LoginRepository;
import com.carparking.project.service.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RateHelper {


    @Autowired
    RatesService ratesService;


    @Autowired
    LoginRepository loginRepository;

    public Map<String, Double> findtotalrate(String time) {
        Rates rates = ratesService.getRates(loginRepository.getActiveUser());
        String[] parts = time.split("\\D+");

        double totalMinutes = (Integer.parseInt(parts[0]) * 60) + Integer.parseInt(parts[1]) + (Integer.parseInt(parts[2]) / 60.0);
        double ratePerMinute = rates.getCharge() / 60.0;
        double totalRate = totalMinutes * ratePerMinute;

        System.out.printf("Total rate for : Rs. %.2f\n", totalRate);
        Map<String, Double> resmap = new HashMap<>();
        resmap.put("total fare", totalRate);
        resmap.put("totalMinutes", ratePerMinute);
        return resmap;
    }
}