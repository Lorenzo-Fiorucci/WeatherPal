package com.univpm.oop.WeatherPal.model.City;

public class GeoPoint {

    private double lat;
    private double lon;
    private short alt;

    GeoPoint(double lat, double lon, short alt){
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public short getAlt() {
        return alt;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setAlt(short alt) {
        this.alt = alt;
    }

}