package com.univpm.oop.WeatherPal.model.Forecast;

import java.time.LocalTime;

public class DailyForecast extends Forecast {
    private LocalTime sunrise;
    private LocalTime sunset;
    private LocalTime moonrise;
    private LocalTime moonset;

    public DailyForecast(LocalTime sunrise, LocalTime sunset, LocalTime moonrise, LocalTime moonset) {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;

    }

    public LocalTime getSunrise() {
        return sunrise;
    }

    public void setSunrise(LocalTime sunrise){
        this.sunrise = sunrise;
    }

    public LocalTime getSunset() {
        return sunset;
    }

    public void setSunset(LocalTime sunset) {
        this.sunset = sunset;
    }

    public LocalTime getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(LocalTime moonrise) {
        this.moonrise = moonrise;
    }

    public LocalTime getMoonset() {
        return moonset;
    }

    public void setMoonset(LocalTime moonset) {
        this.moonset = moonset;
    }

}
