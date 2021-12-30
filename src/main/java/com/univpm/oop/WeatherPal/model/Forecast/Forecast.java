package com.univpm.oop.WeatherPal.model.Forecast;

import com.univpm.oop.WeatherPal.model.JsonSerializers.ForecastSerializer;
import com.univpm.oop.WeatherPal.model.Measures.Measure;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

@JsonSerialize(using = ForecastSerializer.class)
public class Forecast {

    protected Weather weather;
    protected LocalDate date;
    protected Measure<Double> temp;
    protected Measure<Double> feelsLike;
    protected Measure<Byte> humidity;
    protected Measure<Integer> wind;
    protected Measure<Integer> pressure;
    protected Measure<AirPollution> airPoll;
    protected Measure<Byte> clouds;
    protected Measure<Byte> pop;

    public Weather getWeather(){
        return weather;
    }
    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public LocalDate getDate(){
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Measure<Double> getTemp(){
        return temp;
    }
    public void setTemp(Measure<Double> temp) {
        this.temp = temp;
    }

    public Measure<Double> getFeelsLike(){
        return feelsLike;
    }
    public void setFeelsLike(Measure<Double> feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Measure<Byte> getHumidity(){
        return humidity;
    }
    public void setHumidity(Measure<Byte> humidity) {
        this.humidity = humidity;
    }

    public Measure<Integer> getWind(){
        return wind;
    }
    public void setWind(Measure<Integer> wind) {
        this.wind = wind;
    }

    public Measure<Integer> getPressure(){
        return pressure;
    }
    public void setPressure(Measure<Integer> pressure) {
        this.pressure = pressure;
    }

    public Measure<AirPollution> getAirPoll(){
        return airPoll;
    }
    public void setAirPoll(Measure<AirPollution> airPoll) {
        this.airPoll = airPoll;
    }

    public Measure<Byte> getClouds(){
        return clouds;
    }
    public void setClouds(Measure<Byte> clouds) {
        this.clouds = clouds;
    }

    public Measure<Byte> getPop(){
        return pop;
    }
    public void setPop(Measure<Byte> pop) {
        this.pop = pop;
    }
}
