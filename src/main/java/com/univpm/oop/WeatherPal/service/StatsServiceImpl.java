package com.univpm.oop.WeatherPal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.univpm.oop.WeatherPal.exceptions.EmptyVectorException;
import com.univpm.oop.WeatherPal.exceptions.InvalidFormatterException;
import com.univpm.oop.WeatherPal.exceptions.InvalidPeriodException;
import com.univpm.oop.WeatherPal.model.Filters.*;
import com.univpm.oop.WeatherPal.model.Statistics.Stats;
import com.univpm.oop.WeatherPal.model.tools.Check;
import com.univpm.oop.WeatherPal.model.tools.JsonParser;
import org.springframework.stereotype.Service;

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

    public String getHouStats(String city, String day1, String day2, String time1, String time2)
            throws InvalidFormatterException, IOException, InterruptedException, EmptyVectorException, InvalidPeriodException {

        Check.VerPatHou(day1, day2, time1, time2);

        LocalDateTime dateTime1 = LocalDateTime.parse(day1 + " " + time1, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        LocalDateTime dateTime2 = LocalDateTime.parse(day2 + " " + time2, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        HourlyPeriod houPer;
        if(dateTime1.plusHours(1).compareTo(dateTime2) > 0) // se date/time iniziali sono successivi a date/time finali, oppure meno di 1 ora precedenti
            throw new InvalidPeriodException("The period is not valid");
        else
            houPer = new HourlyPeriod(dateTime1, dateTime2);

        String response = "";
        ObjectMapper mapper = new ObjectMapper();
        boolean fromFile = false;
        
        if (city.toLowerCase().equals("ancona")) {
            try {
                Check.VerPerHou(houPer, getHouPer("\\src\\main\\resources\\static\\Every1h"));
                fromFile = true;
                
                response = mapper.writeValueAsString(new Stats(JsonParser.HourlyFile(houPer)));
            } catch (InvalidPeriodException e) {
                // non lancio l'eccezione, ma la gestisco perche' voglio controllare se houPer e' contenuto nel periodo dell'historical api.
                // Quindi non faccio nulla nel catch perche' fromFile rimane false ed entro nell'if dell'else successivo
            }
        } else if(!fromFile){

            HourlyPeriod hisPer = new HourlyPeriod(LocalDate.now().minusDays(5), LocalTime.of(01, 00),
                                                   LocalDate.now(), LocalTime.now());
            Check.VerPerHou(houPer, hisPer);
            
            response = mapper.writeValueAsString(new Stats(JsonParser.fromHistoricalHourly(city, houPer)));
        }
        return response;
    }


    @Override
    public String getDayStats(String city, String day1, String day2)
            throws InvalidFormatterException, IOException, InvalidPeriodException, InterruptedException, EmptyVectorException {

        Check.VerPatDay(day1, day2);

        LocalDate date1 = LocalDate.parse(day1, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate date2 = LocalDate.parse(day2, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        DailyPeriod dayPer;
        
        if(date1.compareTo(date2) > 0)
            throw new InvalidPeriodException("Start date cannot be after end date");
        else
            dayPer = new DailyPeriod(date1, date2);
        
        DailyPeriod hisPer = new DailyPeriod(LocalDate.now().minusDays(5), LocalDate.now());
        Check.VerPerDay(dayPer, hisPer);
        
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(new Stats(JsonParser.fromHistoricalDaily(city, dayPer)));
    }
}