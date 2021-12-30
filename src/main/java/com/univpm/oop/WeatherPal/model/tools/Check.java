package com.univpm.oop.WeatherPal.model.tools;

import com.univpm.oop.WeatherPal.exceptions.InvalidFormatterException;
import com.univpm.oop.WeatherPal.exceptions.InvalidPeriodException;
import com.univpm.oop.WeatherPal.model.Filters.HourlyPeriod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Check {

    public static void VerPatDay(String day1, String day2) throws InvalidFormatterException {

        try {
            LocalDate lclDate1 = LocalDate.parse(day1, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalDate lclDate2 = LocalDate.parse(day2, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        } catch (DateTimeParseException e) {
            throw new InvalidFormatterException("Invalid pattern of date");
        }
    }

    public static void VerPatHou (String day1, String day2, String time1, String time2) throws InvalidFormatterException {

        VerPatDay(day1, day2);
        try {
            LocalTime lclTime1 = LocalTime.parse(time1, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime lclTime2 = LocalTime.parse(time2, DateTimeFormatter.ofPattern("HH:mm"));

        } catch (DateTimeParseException e) {
            throw new InvalidFormatterException("Invalid pattern of time");
        }
    }


    public static void VerPerHou(String day1, String day2, String time1, String time2, HourlyPeriod hourlyPeriod) throws InvalidPeriodException {

        LocalDateTime dateTime1 = LocalDateTime.parse(day1 + " " + time1, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        LocalDateTime dateTime2 = LocalDateTime.parse(day2 + " " + time2, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        if (!(hourlyPeriod.contains(dateTime1) && hourlyPeriod.contains(dateTime2)))
        throw new InvalidPeriodException("This hourly period is not available");
    }

    public static void VerPerDay(String day1, String day2, HourlyPeriod hourlyPeriod) throws InvalidPeriodException {

        LocalDate date1 = LocalDate.parse(day1, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate date2 = LocalDate.parse(day2, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        if (!(hourlyPeriod.contains(date1) && hourlyPeriod.contains(date2)))
        throw new InvalidPeriodException("This daily period is not available");

    }
}