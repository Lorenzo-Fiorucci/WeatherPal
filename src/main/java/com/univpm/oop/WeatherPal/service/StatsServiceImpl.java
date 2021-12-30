package com.univpm.oop.WeatherPal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.univpm.oop.WeatherPal.exceptions.EmptyVectorException;
import com.univpm.oop.WeatherPal.exceptions.InvalidFormatterException;
import com.univpm.oop.WeatherPal.exceptions.InvalidPeriodException;
import com.univpm.oop.WeatherPal.model.Filters.HourlyPeriod;
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
        HourlyPeriod houPer = new HourlyPeriod(dateTime1, dateTime2);

        String response;
        ObjectMapper mapper = new ObjectMapper();

        if (city.toLowerCase().equals("ancona")
                && getHouPer("\\src\\main\\resources\\static\\Every1h").contains(dateTime1)
                && getHouPer("\\src\\main\\resources\\static\\Every1h").contains(dateTime2)) {

            Stats statsObj = new Stats(JsonParser.HourlyFile(houPer));

            response = mapper.writeValueAsString(statsObj);
         } else {

            HourlyPeriod hisPer = new HourlyPeriod(LocalDate.now().minusDays(5), LocalTime.of(01, 00),
                    LocalDate.now(), LocalTime.now());
            if (hisPer.contains(dateTime1) && hisPer.contains(dateTime2)){

                Stats statsObj = new Stats(JsonParser.fromHistoricalHourly(city, houPer));
                response = mapper.writeValueAsString(statsObj);

            } else {

                throw new InvalidPeriodException("This period is not available");
            }
        }

        return response;
    }

/*
    @Override
    public String getHouStats(HourlyPeriod hourlyPeriod) {


    }
*/
    //C:\Users\metti\Documents\VS Code Projects\WeatherPal\src\main\resources\static\Every1h

}