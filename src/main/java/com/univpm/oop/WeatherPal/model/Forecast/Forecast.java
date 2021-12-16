package com.univpm.oop.WeatherPal.model.Forecast;

import com.univpm.oop.WeatherPal.model.Measures.Measure;

import java.time.LocalDate;

public class Forecast {

    private Weather weather;
    private LocalDate date;
    private Measure<Float> temperature;
    private Measure<Float> feelsLike;
    private Measure<Byte> humidity;
    private Measure<Short> wind;
    private Measure<Short> pressure;
    private Measure<Short> airPollution;
    private Measure<Byte> clouds;
    private Measure<Float> pop;
    private Measure<Byte> uv;


}
