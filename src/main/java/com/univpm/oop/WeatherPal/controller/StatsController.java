package com.univpm.oop.WeatherPal.controller;

import com.univpm.oop.WeatherPal.model.Filters.*;
import com.univpm.oop.WeatherPal.model.tools.JsonParser;
import com.univpm.oop.WeatherPal.service.StatsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
public class StatsController {
    
    @Autowired
    StatsService statsService;
    

    @RequestMapping(value = "/5dForecast", method = RequestMethod.GET)
    public ResponseEntity<Object> _5dForecast(@RequestParam("city") String city) {
        
        ResponseEntity<Object> response;
        ObjectMapper mapper = new ObjectMapper();
        try {
            response = new ResponseEntity<>(mapper.writeValueAsString(JsonParser.from5dForecast(city)), HttpStatus.OK);
        } catch(Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }
    

    @RequestMapping(value = "/stats/period", method = RequestMethod.GET)
    public ResponseEntity<Object> getPeriod() {

        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(5), LocalTime.of(01, 00));
        
        HourlyPeriod historicalPeriod = new HourlyPeriod(startDate, endDate);
        HourlyPeriod filePeriod = statsService.getHouPer("\\src\\main\\resources\\static\\Every1h");
        
        String period = "For all the cities, stats are available " + historicalPeriod.toString() + 
                        ". For Ancona they are also available " + filePeriod.toString();
        
        return new ResponseEntity<Object>(period, HttpStatus.OK);
}


    @RequestMapping(value = "/stats/hourly", method = RequestMethod.GET)
    public ResponseEntity<Object> HouStats(@RequestParam(name = "city", defaultValue = "ancona") String city,
                                           @RequestParam(name = "start date") String day1,
                                           @RequestParam(name = "end date", required = false) String day2,
                                           @RequestParam(name = "start time") String time1,
                                           @RequestParam(name = "end time") String time2) {
        ResponseEntity<Object> response;

        if (day2 == null)
            day2 = day1;
        
        try {
            response = new ResponseEntity<Object>(statsService.getHouStats(city, day1, day2, time1, time2), HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }


    @RequestMapping(value = "/stats/daily", method = RequestMethod.GET)
    public ResponseEntity<Object> getDailyStats(@RequestParam(name = "city", defaultValue = "ancona") String city,
                                                @RequestParam(name = "start date") String day1,
                                                @RequestParam(name = "end date") String day2) {

        ResponseEntity<Object> response;

        if (day2 == null)
            day2 = day1;

        try {

            response = new ResponseEntity<Object>(statsService.getDayStats(city, day1, day2), HttpStatus.OK);

        } catch (Exception e){
            response = new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return response;
    }


}
