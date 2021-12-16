package com.univpm.oop.WeatherPal.model.Forecast;

import java.time.LocalTime;

public class HourlyForecast extends Forecast {
    private LocalTime time;

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
