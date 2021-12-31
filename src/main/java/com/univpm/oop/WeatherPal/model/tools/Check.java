package com.univpm.oop.WeatherPal.model.tools;

import com.univpm.oop.WeatherPal.exceptions.InvalidFormatterException;
import com.univpm.oop.WeatherPal.exceptions.InvalidPeriodException;
import com.univpm.oop.WeatherPal.model.Filters.DailyPeriod;
import com.univpm.oop.WeatherPal.model.Filters.HourlyPeriod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Check {

    /**
     * Method to check if {@code day1} and {@code day2} are of the "dd-MM-yyyy" pattern
     * @param day1 : first date to check
     * @param day2 : second date to check
     * @throws InvalidFormatterException if {@code day1} or {@code day2} are not of the "dd-MM-yyyy" pattern
     */
    public static void VerPatDay(String day1, String day2) throws InvalidFormatterException {

        try {
            LocalDate lclDate1 = LocalDate.parse(day1, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalDate lclDate2 = LocalDate.parse(day2, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        } catch (DateTimeParseException e) {
            throw new InvalidFormatterException("Invalid pattern of date");
        }
    }

    /**
     * Method to check if {@code day1} and {@code day2} are of the "dd-MM-yyyy" pattern
     * and if {@code time1} or {@code time2} are of the "HH:mm" pattern
     * @param day1 : first date to check
     * @param day2 : second date to check
     * @param time1 : first time to check
     * @param time2 : second time to check
     * @throws InvalidFormatterException 
     *      if {@code day1} or {@code day2} are not of the "dd-MM-yyyy" pattern
     *      or if {@code time1} or {@code time2} are not of the "HH:mm" pattern
     */
    public static void VerPatHou (String day1, String day2, String time1, String time2) throws InvalidFormatterException {

        VerPatDay(day1, day2);
        try {
            LocalTime lclTime1 = LocalTime.parse(time1, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime lclTime2 = LocalTime.parse(time2, DateTimeFormatter.ofPattern("HH:mm"));

        } catch (DateTimeParseException e) {
            throw new InvalidFormatterException("Invalid pattern of time");
        }
    }



    public static void VerPerHou(HourlyPeriod toCheck, HourlyPeriod hourlyPeriod) throws InvalidPeriodException {

        if(!hourlyPeriod.contains(toCheck))
            throw new InvalidPeriodException("Stats are not available in this period");
    }

    /**
     * Method to check if {@code day1} and {@code day2} are contained in {@code hourlyPeriod}
     * @param day1 : first date to check
     * @param day2 : second date to check
     * @param hourlyPeriod : period in which to do the verification
     * @throws InvalidPeriodException if {@code day1} or {@code day2} are not contained in {@code hourlyPeriod}
     */
    public static void VerPerDay(DailyPeriod toCheck, DailyPeriod dailyPeriod) throws InvalidPeriodException {

        if(!dailyPeriod.contains(toCheck))
            throw new InvalidPeriodException("Stats are not available in this period");

    }
}