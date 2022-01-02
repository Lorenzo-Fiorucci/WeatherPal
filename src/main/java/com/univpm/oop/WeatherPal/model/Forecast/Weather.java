package com.univpm.oop.WeatherPal.model.Forecast;

import com.univpm.oop.WeatherPal.model.JsonSerializers.WeatherSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashMap;

/**
 * Class that represents a weather condition
 */
@JsonSerialize(using = WeatherSerializer.class)
public class Weather {
    private int id;
    private String type;
    private String description;

    public Weather (int id, String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getType(){
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}