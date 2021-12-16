package com.univpm.oop.WeatherPal.model.City;

import com.univpm.oop.WeatherPal.model.Forecast.Forecast;

public class City extends GeoPoint {

    private String name;
    private Forecast prevision;

    City(String name, double lat, double lon, short alt) {
        super(lat, lon, alt);
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Forecast getPrevision() {
        return prevision;
    }

    public void setPrevision(Forecast prevision) {
        this.prevision = prevision;
    }
}
