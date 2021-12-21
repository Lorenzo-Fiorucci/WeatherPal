package com.univpm.oop.WeatherPal.model.Forecast;

import com.univpm.oop.WeatherPal.model.Measures.Measure;

import java.time.LocalDate;

public class Forecast {

    protected Weather weather;
    protected LocalDate date;
    protected Measure<Float> temp;
    protected Measure<Float> feelsLike;
    protected Measure<Byte> humidity;
    protected Measure<Short> wind;
    protected Measure<Short> pressure;
    protected Measure<Short> airPoll;
    protected Measure<Byte> clouds;
    protected Measure<Float> pop;
    protected Measure<Byte> uv;

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

    public Measure<Float> getTemp(){
        return temp;
    }
    public void setTemp(Measure<Float> temp) {
        this.temp = temp;
    }

    public Measure<Float> getFeelsLike(){
        return feelsLike;
    }
    public void setFeelsLike(Measure<Float> feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Measure<Byte> getHumidity(){
        return humidity;
    }
    public void setHumidity(Measure<Byte> humidity) {
        this.humidity = humidity;
    }

    public Measure<Short> getWind(){
        return wind;
    }
    public void setWind(Measure<Short> wind) {
        this.wind = wind;
    }

    public Measure<Short> getPressure(){
        return pressure;
    }
    public void setPressure(Measure<Short> pressure) {
        this.pressure = pressure;
    }

    public Measure<Short> getAirPoll(){
        return airPoll;
    }
    public void setAirPoll(Measure<Short> airPoll) {
        this.airPoll = airPoll;
    }

    public Measure<Byte> getClouds(){
        return clouds;
    }
    public void setClouds(Measure<Byte> clouds) {
        this.clouds = clouds;
    }

    public Measure<Float> getPop(){
        return pop;
    }
    public void setPop(Measure<Float> pop) {
        this.pop = pop;
    }

    public Measure<Byte> getUv(){
        return uv;
    }
    public void setUv(Measure<Byte> uv) {
        this.uv = uv;
    }

}
