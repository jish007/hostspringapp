package com.carparking.project.config;

public class RateCalculator {
    public static void main(String[] args) {
        double baseRatePerHour = 60.0;
        int hours = 1;
        int minutes = 46;
        int seconds = 10;

        double totalMinutes = (hours * 60) + minutes + (seconds / 60.0);
        double ratePerMinute = baseRatePerHour / 60.0;
        double totalRate = totalMinutes * ratePerMinute;

        System.out.printf("Total rate for %d hour(s) %d minute(s) %d second(s): Rs. %.2f\n", hours, minutes, seconds, totalRate);
    }
}