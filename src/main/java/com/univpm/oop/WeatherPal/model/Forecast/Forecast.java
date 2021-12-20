package com.univpm.oop.WeatherPal.model.Forecast;

import com.univpm.oop.WeatherPal.model.Measures.Measure;

import java.time.LocalDate;

public class Forecast {

    private Weather weather;
    private LocalDate date;
    private Measure<Float> temp;
    private Measure<Float> feelsLike;
    private Measure<Byte> humidity;
    private Measure<Short> wind;
    private Measure<Short> pressure;
    private Measure<Short> airPoll;
    private Measure<Byte> clouds;
    private Measure<Float> pop;
    private Measure<Byte> uv;

    public Weather getWeather(){
        return weather;
    }

    public LocalDate getDate(){
        return date;
    }

    public Measure<Float> getTemp(){
        return temp;
    }

    public Measure<Float> getFeelsLike(){
        return feelsLike;
    }

    public Measure<Byte> getHumidity(){
        return humidity;
    }

    public Measure<Short> getWind(){
        return wind;
    }

    public Measure<Short> getPressure(){
        return pressure;
    }

    public Measure<Short> getAirPoll(){
        return airPoll;
    }

    public Measure<Byte> getClouds(){
        return clouds;
    }

    public Measure<Float> getPop(){
        return pop;
    }

    public Measure<Byte> getUv(){
        return uv;
    }

}
