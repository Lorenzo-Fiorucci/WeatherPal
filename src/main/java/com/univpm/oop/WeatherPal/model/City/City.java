package com.univpm.oop.WeatherPal.model.City;

import com.univpm.oop.WeatherPal.model.Forecast.Forecast;
import com.univpm.oop.WeatherPal.model.JsonSerializers.CitySerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Vector;

/**
 * Class that models a city subject of meteorological forecasts.
 * Forecast informations are contained in a vector of {@link Forecast} or its subclasses
 */
@JsonSerialize(using = CitySerializer.class)
public class City extends GeoPoint {

    private String name;
    private Vector<? extends Forecast> forecasts;

    public City(String name, double lat, double lon, int alt) {
        super(lat, lon, alt);
        this.name = name;
    }

    public City(String name, GeoPoint point) {
        super(point.lat, point.lon, point.alt);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<? extends Forecast> getForecasts() {
        return forecasts;
    }

    public <T extends Forecast >void setForecasts(Vector<T> forecasts) {
        this.forecasts = forecasts;
    }
}
