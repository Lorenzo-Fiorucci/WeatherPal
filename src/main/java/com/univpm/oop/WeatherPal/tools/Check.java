package com.univpm.oop.WeatherPal.tools;

import com.univpm.oop.WeatherPal.model.Filters.HourlyPeriod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Check {

    public static boolean VerifyPattern(String day1, String day2, String time1, String time2){

        boolean verify = false;
        try {
            LocalDate lclDate1 = LocalDate.parse(day1, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalDate lclDate2 = LocalDate.parse(day2, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalTime lclTime1 = LocalTime.parse(time1, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime lclTime2 = LocalTime.parse(time2, DateTimeFormatter.ofPattern("HH:mm"));
            verify = true;

        } catch	(DateTimeParseException e){
            e.printStackTrace();
        }
        return verify;
    }

    public static boolean VerifyPeriod(String day1, String day2, String time1, String time2, HourlyPeriod hourlyPeriod) {

        boolean verify = false;
        LocalDateTime dateTime1 = LocalDateTime.parse(day1 + " " + time1, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        LocalDateTime dateTime2 = LocalDateTime.parse(day2 + " " + time2, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        if( hourlyPeriod.contains(dateTime1) && hourlyPeriod.contains(dateTime2))
            verify = true;

        return verify;
    }
}
