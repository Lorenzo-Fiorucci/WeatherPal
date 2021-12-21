package com.univpm.oop.WeatherPal.service;

import com.univpm.oop.WeatherPal.model.Filters.DailyPeriod;
import com.univpm.oop.WeatherPal.model.Filters.HourlyPeriod;
import com.univpm.oop.WeatherPal.model.Measures.Measure;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class StatsServiceImpl implements StatsService {

    @Override
    public void createStats(Measure measure) {

    }

    @Override
    public DailyPeriod getPeriod(String folderPath) {

        String root = System.getProperty("user.dir");
        File folder = new File(root + folderPath);

        String[] fileNames = folder.list();
        String from = fileNames[0].substring(0, fileNames[0].length() - 4);
        String to = fileNames[fileNames.length - 1].substring(0, fileNames[fileNames.length - 1].length() - 4);

        DailyPeriod period = null;

        if (folderPath.equals("\\src\\main\\resources\\static\\Every1h")) {
            LocalDateTime from1 = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm"));
            LocalDateTime to1 = LocalDateTime.parse(to, DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm"));

            period = new HourlyPeriod(from1, to1);
        }
        if (folderPath.equals("\\src\\main\\resources\\static\\Weekly")) {
            LocalDate from2 = LocalDate.parse(from, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalDate to2 = LocalDate.parse(to, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            period = new DailyPeriod(from2, to2);
        }

        return period;
    }
}
/*
    @Override
    public String getHourlyStats(HourlyPeriod hourlyPeriod) {


    }
*/
    //C:\Users\metti\Documents\VS Code Projects\WeatherPal\src\main\resources\static\Every1h