package com.univpm.oop.WeatherPal.exceptions;

/**
 * Exception thrown if the period contained in the request to WeatherPal API is not a valid period or does not contain available weather forecasts.
 */
public class InvalidPeriodException extends Exception {

	public InvalidPeriodException() {
		super();
	}


    public InvalidPeriodException(String message) {
        super(message);
    }
}
