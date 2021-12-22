package com.univpm.oop.WeatherPal.model.City;

public class GeoPoint {

    private double lat;
    private double lon;
    private int alt;

    public GeoPoint(double lat, double lon, int alt) {
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
    }

    public GeoPoint(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getAlt() {
        return alt;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }

}