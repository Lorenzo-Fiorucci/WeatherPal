package com.univpm.oop.WeatherPal.model.Forecast;

import java.time.LocalTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.univpm.oop.WeatherPal.model.JsonSerializers.HourlyForecastSerializer;

@JsonSerialize(using = HourlyForecastSerializer.class)
public class HourlyForecast extends Forecast {
    private LocalTime time;

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
