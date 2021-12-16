package com.univpm.oop.WeatherPal.controller;

import com.univpm.oop.WeatherPal.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {

    @Autowired
    StatsService statsService;

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public ResponseEntity<Object> getPeriod() {
        return new ResponseEntity<Object>(statsService.getPeriod(), HttpStatus.OK);
    }
    /*
    @RequestMapping(value = "/stats/hourly", method = RequestMethod.GET)
    public ResponseEntity<>   {
        return new
    }

    @RequestMapping(value = "/stats/daily", method = RequestMethod.GET)
    public ResponseEntity<>   {
        return new
    }

    @RequestMapping(value = "/stats/weekly", method = RequestMethod.GET)
    public ResponseEntity<>   {
        return new
    }

    @RequestMapping(value = "/5dForecast", method = RequestMethod.GET)
    public ResponseEntity<>   {
        return new
    }
    */
}
