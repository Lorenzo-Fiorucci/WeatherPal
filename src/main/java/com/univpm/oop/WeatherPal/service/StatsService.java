package com.univpm.oop.WeatherPal.service;

import com.univpm.oop.WeatherPal.model.Filters.HourlyPeriod;
import com.univpm.oop.WeatherPal.model.Measures.Measure;

public interface StatsService {

    public abstract void createStats(Measure measure);

    HourlyPeriod getHouPer(String folderPath);


}
