package com.univpm.oop.WeatherPal.service;

import com.univpm.oop.WeatherPal.exceptions.InvalidFormatterException;
import com.univpm.oop.WeatherPal.exceptions.InvalidPeriodException;
import com.univpm.oop.WeatherPal.model.Filters.HourlyPeriod;

import java.io.IOException;

public interface StatsService {

	/**
	 * Method that returns an {@code HourlyPeriod} object that represents the period of the measures 
	 * of the folder specified by {@code folderpath}
	 * @param folderPath : path of files' folder
	 * @return the period of the measures in that folder
	 */
    public HourlyPeriod getHouPer(String folderPath);

	
	/**
	 * Method that returns the message for the route /stats/hourly
	 * @param city : name of the city on which make stats
	 * @param day1 : start date for the statistics
	 * @param day2 : end date for the statistics
	 * @param time1 : start time for the statistics
	 * @param time2 : end time for the statistics
	 * @return the json response of the /stats/hourly route
	 * @throws InvalidFormatterException
	 * 		if {@code day1} or {@code day2} are not of the "dd-MM-yyyy" pattern
	 * 		or if {@code time1} or {@code time2} are not of the "HH:mm" pattern
	 * @throws InvalidPeriodException
	 * 		if the the period trom {@code day1} at {@code time1} to {@code day2} at {@code time2} doesn't contain available measures
	 * @throws IOException if an I/O error occurs while sending a request to an OpenWeatherMap's API or while reading from a file
	 * @throws InterruptedException if the operation of sending a request to an OpenWeatherMap's API is interrupted
	 */

    public String getHouStats(String city, String day1, String day2, String time1, String time2)
        throws InvalidFormatterException, IOException, InterruptedException, InvalidPeriodException;

	
	/**
	 * Method that returns the message for the route /stats/daily
	 * @param city : name of the city on which make stats
	 * @param day1 : start date for the statistics
	 * @param day2 : end date for the statistics
	 * @return the json response or the /stats/daily routr
	 * @throws InvalidFormatterException if {@code day1} or {@code day2} are not of the "dd-MM-yyyy" pattern
	 * @throws InvalidPeriodException if the the period trom {@code day1} to {@code day2} doesn't contain available measures
	 * @throws IOException if an I/O error occurs while sending a request to an OpenWeatherMap's API or while reading from a file
	 * @throws InterruptedException if the operation of sending a request to an OpenWeatherMap's API is interrupted
	 */

    public String getDayStats(String city, String  day1, String day2)
        throws InvalidFormatterException, IOException, InterruptedException, InvalidPeriodException;

}
