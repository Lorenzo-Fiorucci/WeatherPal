package com.univpm.oop.WeatherPal.service;

import com.univpm.oop.WeatherPal.exceptions.*;
import com.univpm.oop.WeatherPal.model.Filters.*;
import com.univpm.oop.WeatherPal.model.Statistics.Stats;
import com.univpm.oop.WeatherPal.model.tools.Check;
import com.univpm.oop.WeatherPal.model.tools.JsonParser;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class StatsServiceImpl implements StatsService {

    @Override
    public HourlyPeriod getHouPer(String folderPath) {

        String root = System.getProperty("user.dir");
        File folder = new File(root + folderPath);

        String[] fileNames = folder.list();
        String from = fileNames[0].substring(0, fileNames[0].length() - 4);
        String to = fileNames[fileNames.length - 1].substring(0, fileNames[fileNames.length - 1].length() - 4);

        HourlyPeriod period = null;

        if (folderPath.equals("\\src\\main\\resources\\static\\Every1h")) {
            LocalDateTime from1 = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm"));
            LocalDateTime to1 = LocalDateTime.parse(to, DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm"));

            period = new HourlyPeriod(from1, to1);
        }

        return period;
    }

    /**
     * Method that returns the message of WeatherPal response to /stats/hourly rout
     */
    @Override
    public String getHouStats(String city, String day1, String day2, String time1, String time2)
            throws InvalidFormatterException, IOException, InterruptedException, InvalidPeriodException {

        Check.VerPatHou(day1, day2, time1, time2);

        LocalDateTime dateTime1 = LocalDateTime.parse(day1 + " " + time1, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        LocalDateTime dateTime2 = LocalDateTime.parse(day2 + " " + time2, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        HourlyPeriod houPer;
        if(dateTime1.plusHours(1).isAfter(dateTime2))
            throw new InvalidPeriodException("Start date/time is after (or less than an hour before) end date/time");
        else
            houPer = new HourlyPeriod(dateTime1, dateTime2);

        String response;
        ObjectMapper mapper = new ObjectMapper();

        if (city.toLowerCase().equals("ancona") && getHouPer("\\src\\main\\resources\\static\\Every1h").contains(houPer)) {
            try {
                response = mapper.writeValueAsString(new Stats(JsonParser.HourlyFile(houPer)));
            } catch (EmptyVectorException e) { // lanciata dal costruttore di Stats se JsonParser restituisce un vettore vuoto
                throw new InvalidPeriodException("No measures found in that period");
            }
        } 
        else {
            HourlyPeriod hisPer = new HourlyPeriod(LocalDate.now().minusDays(5), LocalTime.of(01, 00),
                                                   LocalDate.now(), LocalTime.now());
            if(hisPer.contains(houPer)) {
                try {
                    response = mapper.writeValueAsString(new Stats(JsonParser.fromHistoricalHourly(city, houPer)));
                } catch (EmptyVectorException e) {
                    throw new InvalidPeriodException("No measures found in this period");
                }
            }
            else throw new InvalidPeriodException("Stats are not available in this period.\nConsult the \"/stats/period\" route to see the allowed period");
        }
        return response;
    }

    @Override
    public String getDayStats(String city, String day1, String day2)
            throws InvalidFormatterException, IOException, InvalidPeriodException, InterruptedException {

        Check.VerPatDay(day1, day2);

        LocalDate date1 = LocalDate.parse(day1, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate date2 = LocalDate.parse(day2, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        DailyPeriod dayPer;
        
        if(date1.isAfter(date2))
            throw new InvalidPeriodException("Start date cannot be after end date");
        else
            dayPer = new DailyPeriod(date1, date2);
        
        DailyPeriod hisPer = new DailyPeriod(LocalDate.now().minusDays(5), LocalDate.now());
        if(hisPer.contains(dayPer)) {
            
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(new Stats(JsonParser.fromHistoricalDaily(city, dayPer)));
            } catch (EmptyVectorException e) {
                throw new InvalidPeriodException("No measures found in this period");
            }
        }
        else throw new InvalidPeriodException("Stats are not available in this period.\nConsult the \"/stats/period\" route to see the allowed period");
    }
}