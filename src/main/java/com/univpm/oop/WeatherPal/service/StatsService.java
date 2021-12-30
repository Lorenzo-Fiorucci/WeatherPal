package com.univpm.oop.WeatherPal.service;

import com.univpm.oop.WeatherPal.exceptions.EmptyVectorException;
import com.univpm.oop.WeatherPal.exceptions.InvalidFormatterException;
import com.univpm.oop.WeatherPal.exceptions.InvalidPeriodException;
import com.univpm.oop.WeatherPal.model.Filters.HourlyPeriod;

import java.io.IOException;

public interface StatsService {

    public HourlyPeriod getHouPer(String folderPath);

    public String getHouStats(String city, String day1, String day2, String time1, String time2)
            throws InvalidFormatterException, IOException, InterruptedException, EmptyVectorException, InvalidPeriodException;

    public String getDayStats(String city, String  day1, String day2)
            throws InvalidFormatterException, IOException, InterruptedException, EmptyVectorException, InvalidPeriodException;

}
