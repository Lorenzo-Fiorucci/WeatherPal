package com.univpm.oop.WeatherPal.model.Forecast;

import java.time.LocalTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.univpm.oop.WeatherPal.model.JsonSerializers.HourlyForecastSerializer;

/**
 * Class that represents a meteorological forecast for a specific hour in a future day
 */
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
